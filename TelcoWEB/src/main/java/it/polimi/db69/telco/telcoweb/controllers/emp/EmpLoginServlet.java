package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.Employee;
import it.polimi.db69.telco.telcoejb.services.EmployeeService;
import it.polimi.db69.telco.telcoweb.exceptions.InputException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EmpLoginServlet", value = "/employeelogin")
public class EmpLoginServlet extends HttpServlet {
    TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/EmployeeService")
    EmployeeService employeeService;

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
        String path = "/WEB-INF/employeelogin.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            checkInputs(username, password);
        } catch (InputException e){
            response.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("signinErrorMessage", e.getMessage());

            templateEngine.process("/WEB-INF/employeelogin.html", ctx, response.getWriter());
            return;
        }

        Employee employee;
        try {
            employee = employeeService.checkCredentials(username, password);
        } catch (NonUniqueResultException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials.");
            return;
        }

        if (employee == null) {
            response.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("signinErrorMessage", "Incorrect username or password.");

            templateEngine.process("/WEB-INF/employeelogin.html", ctx, response.getWriter());
        } else {
            /*
            try {
                userService.addLoginLog(user.getId());
            } catch (BadUserException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                return;
            }
            */

            request.getSession().setAttribute("employee", employee);
            response.sendRedirect(getServletContext().getContextPath() + "/employeehomepage");
        }
    }

    private void checkInputs(String username, String password) throws InputException {
        if(username == null || username.isEmpty()){
            throw new InputException("Please insert a username!");
        }

        if(password == null || password.isEmpty()){
            throw new InputException("Please insert a password!");
        }
    }
}
