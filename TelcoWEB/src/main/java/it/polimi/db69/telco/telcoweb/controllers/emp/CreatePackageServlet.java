package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.exceptions.NonUniqueException;
import it.polimi.db69.telco.telcoejb.services.ServicePackageService;
import it.polimi.db69.telco.telcoejb.services.ServiceService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreatePackageServlet", value = "/createpackage")
public class CreatePackageServlet extends HttpServlet {
    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServiceService")
    ServiceService serviceService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServicePackageService")
    ServicePackageService packageService;

    TemplateEngine templateEngine;

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
        String path = "/WEB-INF/employee/createpackage.html";

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String packageName = request.getParameter("packageName");

        //todo: server-side validation

        try {
            ServicePackage servicePackage = packageService.createServicePackage(packageName);

            for(int i = 0; i < Integer.parseInt(request.getParameter("serviceNum")); i++){
                String serviceType = request.getParameter("type" + (i+1));
                int minutes, sms, giga;
                double minutesFee, smsFee, gigaFee;
                switch(serviceType) {
                    case "mobilephone":
                        minutes = Integer.parseInt(request.getParameter("min" + (i + 1)));
                        minutesFee = Double.parseDouble(request.getParameter("mfee" + (i + 1)));
                        sms = Integer.parseInt(request.getParameter("sms" + (i + 1)));
                        smsFee = Double.parseDouble(request.getParameter("sfee" + (i + 1)));
                        serviceService.createMobilePhone(minutes, sms, minutesFee, smsFee, servicePackage.getId());
                        break;
                    case "mobileinternet":
                    case "fixedinternet":
                        giga = Integer.parseInt(request.getParameter("gb" + (i + 1)));
                        gigaFee = Double.parseDouble(request.getParameter("gfee" + (i + 1)));
                        serviceService.createInternet(serviceType, giga, gigaFee, servicePackage.getId());
                        break;
                    case "fixedphone":
                        serviceService.createFixedPhone(servicePackage.getId());
                        break;
                }

                request.getSession().setAttribute("successMessage", "Package successfully created!");
                response.sendRedirect(getServletContext().getContextPath() + "/employeehomepage");
                return;
            }
        } catch (NonUniqueException e) {
            e.printStackTrace();
        }
    }
}
