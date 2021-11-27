package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.Employee;
import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.services.EmployeeService;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(name = "EmpHomeServlet", value = "/employeehomepage")
public class EmpHomeServlet extends HttpServlet {
    TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/EmployeeService")
    EmployeeService employeeService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;

    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        Employee employee = (Employee) req.getSession().getAttribute("employee");

        if(req.getSession().getAttribute("successMessage") != null){
            ctx.setVariable("successMessage", req.getSession().getAttribute("successMessage"));
        }
        if(req.getSession().getAttribute("errorMessage") != null){
            ctx.setVariable("errorMessage", req.getSession().getAttribute("errorMessage"));
        }

        if(employee != null){
            ctx.setVariable("welcomeMessage", "Welcome back, " + employee.getUsername() + "!");
        }

        Collection<Product> products = productService.findAllProducts();
        ctx.setVariable("products", products);

        String path = "/WEB-INF/employee/emphomepage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    public void destroy() {
    }
}
