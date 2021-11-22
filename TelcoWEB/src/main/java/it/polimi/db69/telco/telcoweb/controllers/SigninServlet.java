package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.UserService;
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
import java.util.regex.Pattern;

@WebServlet(name = "SigninServlet", value = "/signin")
public class SigninServlet extends HttpServlet {
    private TemplateEngine templateEngine;

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
        response.sendRedirect(getServletContext().getContextPath());
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

            templateEngine.process("/WEB-INF/index.html", ctx, response.getWriter());
            return;
        }

        User user;
        try {
            user = userService.checkCredentials(username, password);
        } catch (NonUniqueResultException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials.");
            return;
        }

        if (user == null) {
            response.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("signinErrorMessage", "Incorrect username or password.");

            templateEngine.process("/WEB-INF/index.html", ctx, response.getWriter());
        } else {
            /*
            try {
                userService.addLoginLog(user.getId());
            } catch (BadUserException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                return;
            }
            */

            request.getSession().setAttribute("user", user);
            response.sendRedirect(getServletContext().getContextPath() + "/homepage");
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
