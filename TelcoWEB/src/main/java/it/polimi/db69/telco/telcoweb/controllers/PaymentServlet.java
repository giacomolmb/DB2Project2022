package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.services.OrderService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PaymentServlet", value = "/payment")
public class PaymentServlet extends HttpServlet {
    private TemplateEngine templateEngine;

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

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String path = "/WEB-INF/payment.html";

        ctx.setVariable("amount", String.format("%.2f", (Double) request.getSession().getAttribute("amount")));

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        int orderId = (int) request.getSession().getAttribute("orderId");

        if(action.equals("success")){
            orderService.confirmOrder(orderId);
            request.getSession().setAttribute("successMessage", "Order successfully placed!");
        } else if(action.equals("failure")){
            orderService.rejectOrder(orderId);
            request.getSession().setAttribute("errorMessage", "Order rejected by the payment service. You can try again from your profile page.");
        }

        request.removeAttribute("orderId");
        response.sendRedirect(getServletContext().getContextPath() + "/homepage");
    }

    public void destroy() {
    }
}
