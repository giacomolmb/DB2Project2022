package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.entities.Subscription;
import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import it.polimi.db69.telco.telcoejb.services.ServicePackageService;
import it.polimi.db69.telco.telcoejb.services.SubscriptionService;
import it.polimi.db69.telco.telcoejb.services.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

@WebServlet(name = "BuyPackageServlet", value = "/buypackage")
public class BuyPackageServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String path = "/WEB-INF/buypackage.html";

        int packageid = Integer.parseInt(request.getParameter("id"));
        ServicePackage selectedPackage = packageService.findPackageById(packageid);

        ctx.setVariable("package", selectedPackage);

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        java.util.Date startDate;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startDate"));
            Date startSQLDate =  new java.sql.Date(startDate.getTime());

            int validityPeriod = Integer.parseInt(request.getParameter("vp"));

            Subscription subscription = subscriptionService.createSubscription(startSQLDate, validityPeriod);

            if(request.getParameter("optionalProducts") != null){
                for(String optProd : request.getParameterValues("optionalProducts")){
                    subscriptionService.addProductToSubscription(Integer.parseInt(optProd), subscription.getId());
                }
            }

            request.getSession().setAttribute("subscription",subscription);

            response.sendRedirect(getServletContext().getContextPath() + "/confirmation");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
