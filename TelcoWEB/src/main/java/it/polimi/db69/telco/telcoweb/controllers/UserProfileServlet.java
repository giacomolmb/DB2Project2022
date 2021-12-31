package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.User;
import it.polimi.db69.telco.telcoejb.services.OrderService;
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

@WebServlet(name = "UserProfileServlet", value = "/userprofile")
public class UserProfileServlet extends HttpServlet {
    TemplateEngine templateEngine;

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
        String path = "/WEB-INF/userprofile.html";

        if(request.getSession().getAttribute("user") != null){
            User user = (User) request.getSession().getAttribute("user");
            user = userService.findUserById(user.getId());
            ctx.setVariable("user", user.getUsername());
            ctx.setVariable("orders", user.getUserOrders());
            ctx.setVariable("activeorders", user.getActiveOrders());
        }
        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("orderId", Integer.parseInt(request.getParameter("orderId")));
        request.getSession().setAttribute("amount", Double.parseDouble(request.getParameter("amount")));
        response.sendRedirect(getServletContext().getContextPath() + "/payment");
    }
}
