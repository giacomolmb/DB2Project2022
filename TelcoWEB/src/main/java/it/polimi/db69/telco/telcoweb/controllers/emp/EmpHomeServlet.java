package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.Employee;
import it.polimi.db69.telco.telcoejb.services.EmployeeService;
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

@WebServlet(name = "EmpHomeServlet", value = "/employeehomepage")
public class EmpHomeServlet extends HttpServlet {
    TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/EmployeeService")
    EmployeeService employeeService;

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

        Employee employee = (Employee) req.getSession().getAttribute("employee");

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        if(employee != null){
            ctx.setVariable("welcomeMessage", "Welcome back, " + employee.getUsername() + "!");
        }

        String path = "/WEB-INF/employee/emphomepage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    public void destroy() {
    }
}
