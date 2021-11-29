package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.Order;
import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.entities.Subscription;
import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.OrderService;
import it.polimi.db69.telco.telcoejb.services.ServicePackageService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet(name = "ConfirmationServlet", value = "/confirmation")
public class ConfirmationServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServicePackageService")
    private ServicePackageService packageService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/OrderService")
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String path = "/WEB-INF/confirmationpage.html";

        Subscription subscription = ((Subscription) request.getSession().getAttribute("subscription"));
        //request.getSession().removeAttribute("subscription");

        ServicePackage servicePackageSelected = subscription.getSubValidityPeriod().getVpPackage();

        ctx.setVariable("subscription", subscription);
        ctx.setVariable("package", servicePackageSelected);
        ctx.setVariable("user", user);

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subscription subscription = (Subscription) request.getSession().getAttribute("subscription");
        orderService.createSubscription(subscription);

        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        int userId = ((User)request.getSession().getAttribute("user")).getId();

        Order order = orderService.createOrder(timestamp, userId, subscription.getId());

        request.getSession().setAttribute("order", order);

        response.sendRedirect(getServletContext().getContextPath() + "/hello-servlet");
    }

    public void destroy() {
    }

}
