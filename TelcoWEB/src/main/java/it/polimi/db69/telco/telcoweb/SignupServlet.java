package it.polimi.db69.telco.telcoweb;

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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


@WebServlet(name = "SignupServlet", value = "/SignupServlet")
public class SignupServlet extends HttpServlet {

    private TemplateEngine templateEngine;
    @EJB(name = "it.polimi.db69.telco.telcoejb.services/UserService")
    private UserService userService;
    private Object StringEscapeUtils;

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

        templateEngine.process("/WEB-INF/login.html", ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("reg-email");
        String username = req.getParameter("reg-username");
        String password = req.getParameter("reg-password");
        String confirmPassword = req.getParameter("reg-conf-password");

        //mancano tutti i check

        try {
            User user = userService.createUser(email, username, password);
        } catch (CredentialException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(getServletContext().getContextPath()+"/hello-servlet");

    }
}
