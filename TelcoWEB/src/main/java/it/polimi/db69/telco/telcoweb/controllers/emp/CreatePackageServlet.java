package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.exceptions.NonUniqueException;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import it.polimi.db69.telco.telcoejb.services.ServicePackageService;
import it.polimi.db69.telco.telcoejb.services.ServiceService;
import it.polimi.db69.telco.telcoejb.services.ValidityPeriodService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "CreatePackageServlet", value = "/employee/createpackage")
public class CreatePackageServlet extends HttpServlet {
    private String path = "/WEB-INF/employee/emphomepage.html";

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServiceService")
    ServiceService serviceService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServicePackageService")
    ServicePackageService packageService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ValidityPeriodService")
    private ValidityPeriodService vpService;

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
        path = "/WEB-INF/employee/createpackage.html";

        Collection<Product> products = productService.findAllProducts();
        ctx.setVariable("products", products);

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

            }

            for(int i = 0; i < Integer.parseInt(request.getParameter("vpNum")); i++) {
                int vpMonths = Integer.parseInt(request.getParameter("vp" + (i + 1)));
                double vpFee = Double.parseDouble(request.getParameter("vpf" + (i + 1)));
                vpService.createValidityPeriod(vpMonths, vpFee, servicePackage.getId());
            }

            if(request.getParameter("optionalProducts") != null){
                for(String optProd : request.getParameterValues("optionalProducts")){
                    packageService.addProduct(Integer.parseInt(optProd), servicePackage.getId());
                }
            }

            request.getSession().setAttribute("successMessage", "Package successfully created!");
            response.sendRedirect(getServletContext().getContextPath() + "/employee/homepage");
        } catch (NonUniqueException e) {
            e.printStackTrace();
        }
    }
}
