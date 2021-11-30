package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.ServicePackageService;
import it.polimi.db69.telco.telcoejb.services.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "HomepageServlet", value = "/homepage")
public class HomepageServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/UserService")
    private UserService userService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServicePackageService")
    private ServicePackageService servicePackageService;


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
        String path = "/WEB-INF/homepage.html";

        HttpSession session = req.getSession();
        session.setAttribute("origin", "/homepage");

        Collection<ServicePackage> packages = servicePackageService.findAllPackages();

        if(session.getAttribute("subscription") != null){
            session.removeAttribute("subscription");
        }
        if(session.getAttribute("user") != null){
            User user = (User) session.getAttribute("user");
            ctx.setVariable("user", user.getUsername());
        }
        if(req.getSession().getAttribute("successMessage") != null){
            ctx.setVariable("successMessage", req.getSession().getAttribute("successMessage"));
            req.getSession().removeAttribute("successMessage");
        }
        if(req.getSession().getAttribute("errorMessage") != null){
            ctx.setVariable("errorMessage", req.getSession().getAttribute("errorMessage"));
            req.getSession().removeAttribute("errorMessage");
        }
        ctx.setVariable("packages", packages );

        templateEngine.process(path, ctx, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


}
