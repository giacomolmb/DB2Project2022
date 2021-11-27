package it.polimi.db69.telco.telcoweb.controllers;

import it.polimi.db69.telco.telcoejb.entities.Subscription;
import it.polimi.db69.telco.telcoejb.entities.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ConfirmationServlet", value = "/confirmation")
public class ConfirmationServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Subscription subscription= (Subscription) request.getSession().getAttribute("subscription");

        if(subscription != null){
            message = "Subscription id, " + subscription.getId();
        }

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }

}
