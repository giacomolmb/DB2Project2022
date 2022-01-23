package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.*;
import it.polimi.db69.telco.telcoejb.enums.ServiceType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Stateless
public class ServiceService {
    @PersistenceContext (unitName = "TELCOEJB")
    private EntityManager em;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServicePackageService")
    private ServicePackageService packageService;

    public Service findServiceById(int serviceId){
        return em.find(Service.class, serviceId);
    }

    public Service createMobilePhone(int min, int sms, double minFee, double smsFee, int packageId){
        Service service = new MobilePhone(min, sms, minFee, smsFee);
        service.setServicePackage(packageService.findPackageById(packageId));
        em.persist(service);

        return service;
    }

    public Service createFixedPhone(int packageId){
        Service service = new FixedPhone();
        service.setServicePackage(packageService.findPackageById(packageId));
        em.persist(service);

        return service;
    }

    public Service createInternet(String type, int giga, double gigaFee, int packageId){
        Service service;
        if(type.equals("mobileinternet")){
            service = new MobileInternet(giga, gigaFee);
        } else {
            service = new FixedInternet(giga, gigaFee);
        }
        service.setServicePackage(packageService.findPackageById(packageId));
        em.persist(service);

        return service;
    }



}
