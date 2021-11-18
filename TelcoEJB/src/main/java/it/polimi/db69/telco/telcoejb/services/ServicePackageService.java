package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.entities.Service;
import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.exceptions.NonUniqueException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServiceService")
    private ServiceService serviceService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;


    public ServicePackage findPackageById(int packageId){
        return em.find(ServicePackage.class, packageId);
    }

    public ServicePackage createServicePackage(String name) throws NonUniqueException {
        if (em.createNamedQuery("package.findByName", ServicePackage.class)
                .setParameter("name", name).setMaxResults(1).getResultStream().findFirst().orElse(null) != null)
            throw new NonUniqueException("The package \"" + name + "\" already exists!");

        ServicePackage servicePackage = new ServicePackage(name);
        em.persist(servicePackage);

        return servicePackage;
    }

    public ServicePackage addService(int serviceId, int packageId){
        ServicePackage servicePackage = findPackageById(packageId);
        Service service = serviceService.findServiceById(serviceId);

        ArrayList<Service> services = new ArrayList<>();
        services.add(service);
        servicePackage.setServices(services);

        return servicePackage;
    }

    public ServicePackage addProduct (int productId, int packageId){
        ServicePackage servicePackage = findPackageById(packageId);
        Product product = productService.findProductById(productId);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        servicePackage.setProducts(products);

        return servicePackage;
    }
}
