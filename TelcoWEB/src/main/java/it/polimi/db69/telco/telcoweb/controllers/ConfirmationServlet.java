package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.entities.Subscription;
import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import it.polimi.db69.telco.telcoejb.services.ServicePackageService;
import it.polimi.db69.telco.telcoejb.services.SubscriptionService;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(name = "ConfirmationServlet", value = "/confirmation")
public class ConfirmationServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServicePackageService")
    private ServicePackageService packageService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/SubscriptionService")
    private SubscriptionService subscriptionService;

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
        String path = "/WEB-INF/confirmationpage.html";

        Subscription subscription = (Subscription) request.getSession().getAttribute("subscription");

        ServicePackage servicePackageSelected = subscription.getSubValidityPeriod().getVpPackage();


        ctx.setVariable("subscription", subscription);
        ctx.setVariable("package", servicePackageSelected);

        templateEngine.process(path, ctx, response.getWriter());
    }

    public void destroy() {
    }

}
