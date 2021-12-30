package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.PackageSales;
import it.polimi.db69.telco.telcoejb.entities.ProductSales;
import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import it.polimi.db69.telco.telcoejb.services.SalesReportService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@WebServlet(name = "SalesReportServlet", value = "/employee/salesreport")
public class SalesReportServlet extends HttpServlet {
    private boolean onlyInsolventUsers = true;

    TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/SalesReportService")
    private SalesReportService salesService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/UserService")
    private UserService userService;

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
        String path = "/WEB-INF/employee/salesreport.html";

        Collection<PackageSales> sales = salesService.getAllSales();
        Collection<ProductSales> products = productService.getSales();
        Collection<User> users = userService.getInsolventUsers();

        if(request.getParameter("type") != null){
            if(Objects.equals(request.getParameter("type"), "insolventUsers")){
                this.onlyInsolventUsers = !this.onlyInsolventUsers;
                if(!this.onlyInsolventUsers){
                    users = userService.getAllUsers();
                }
                ctx.setVariable("activeTab", 3);
            }
            if(Objects.equals(request.getParameter("type"), "filterPackages")){
                if(!Objects.equals(request.getParameter("packageId"), "")){
                    sales = salesService.getSalesByPackage(Integer.parseInt(request.getParameter("packageId")));
                }
                ctx.setVariable("activeTab", 1);
            }
            if(Objects.equals(request.getParameter("type"), "filterProducts")){
                if(!Objects.equals(request.getParameter("productId"), "")){
                    products = productService.getSingleProductSales(Integer.parseInt(request.getParameter("productId")));
                }
                ctx.setVariable("activeTab", 2);
            }
        } else {
            ctx.setVariable("activeTab", 1);
        }

        ctx.setVariable("insolvent", this.onlyInsolventUsers);
        ctx.setVariable("sales", sales);
        ctx.setVariable("products", products);
        ctx.setVariable("users", users);

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
