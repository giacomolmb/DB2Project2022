package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.Employee;
import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.exceptions.NonUniqueException;
import it.polimi.db69.telco.telcoejb.services.EmployeeService;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import it.polimi.db69.telco.telcoweb.exceptions.InputException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateProductServlet", value = "/createproduct")
public class CreateProductServlet extends HttpServlet {
    TemplateEngine templateEngine;

    private String path = "/WEB-INF/employee/emphomepage.html";

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    ProductService productService;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        path = "/WEB-INF/employee/createproduct.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String productFee = request.getParameter("productFee");

        try{
            checkInputs(productName, productFee);
            Product product = productService.createProduct(productName, Double.parseDouble(productFee));
        } catch (NonUniqueException | InputException e) {
            response.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("errorMessage", e.getMessage());

            templateEngine.process(path, ctx, response.getWriter());
            return;
        }

        request.getSession().setAttribute("successMessage", "Product successfully created!");
        response.sendRedirect(getServletContext().getContextPath() + "/employeehomepage");
    }

    private void checkInputs(String productName, String fee) throws InputException{
        if(productName == null || productName.isEmpty()){
            throw new InputException("Invalid input! Please insert a product name");
        }

        if(fee == null || fee.isEmpty()){
            throw new InputException("Invalid input! Please insert a product monthly fee!");
        }

        try{
            Double.parseDouble(fee);
        } catch (NumberFormatException e){
            throw new InputException("Invalid input! Please insert a valid monthly fee!");
        }
    }
}
