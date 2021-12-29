package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.PackageSales;
import it.polimi.db69.telco.telcoejb.entities.ProductSales;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import it.polimi.db69.telco.telcoejb.services.SalesReportService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "SalesReportServlet", value = "/employee/salesreport")
public class SalesReportServlet extends HttpServlet {
    TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/SalesReportService")
    private SalesReportService salesService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;

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

        ctx.setVariable("sales", sales);
        ctx.setVariable("products", products);

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
