package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.UserService;

import javax.ejb.EJB;
import javax.security.auth.login.CredentialException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

import it.polimi.db69.telco.telcoweb.exceptions.InputException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "SignupServlet", value = "/signup")
public class SignupServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/UserService")
    private UserService userService;

    private String path = "/WEB-INF/index.html";

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        path = "/WEB-INF/register.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("reg-email");
        String username = req.getParameter("reg-username");
        String password = req.getParameter("reg-password");
        String confirmPassword = req.getParameter("reg-conf-password");

        try{
            checkInputs(username, password, confirmPassword, email);
        } catch (InputException e){
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("signupErrorMessage", e.getMessage());

            templateEngine.process(path, ctx, resp.getWriter());
            return;
        }

        try {
            User user = userService.createUser(email, username, password);
        } catch (CredentialException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(getServletContext().getContextPath()+"/signin");

    }

    private void checkInputs(String username, String password, String confirmPassword, String email) throws InputException {
        if(username == null || username.isEmpty()){
            throw new InputException("Please insert a username!");
        }

        if(email == null || email.isEmpty()){
            throw new InputException("Please insert an Email!");
        }

        if(password == null || password.isEmpty()){
            throw new InputException("Please insert a password!");
        }

        if(confirmPassword == null || confirmPassword.isEmpty()){
            throw new InputException("Please confirm the password!");
        }

        if (username.length() > 45) {
            throw new InputException("Username is too long (max 45 chars)!");
        }

        if (email.length() > 45) {
            throw new InputException("Email is too long (max 45 chars)!");
        }

        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        if (!pattern.matcher(email).matches()) {
            throw new InputException("Invalid email address!");
        }

        if (password.length() > 45 || password.length() < 8) {
            throw new InputException("Password length not valid (min 8 max 45 chars)!");
        }

        if (!confirmPassword.equals(password)) {
            throw new InputException("Password entered are not the same!");
        }
    }
}
