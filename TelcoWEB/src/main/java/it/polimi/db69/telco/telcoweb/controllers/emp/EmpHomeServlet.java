package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.Employee;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EmpHomeServlet", value = "/employeehomepage")
public class EmpHomeServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        Employee employee = (Employee) request.getSession().getAttribute("employee");

        if(employee != null){
            message = "Welcome back, employee " + employee.getUsername();
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
