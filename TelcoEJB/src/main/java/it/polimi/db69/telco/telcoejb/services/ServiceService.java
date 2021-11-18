package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.*;
import it.polimi.db69.telco.telcoejb.enums.ServiceType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Stateless
public class ServiceService {

    @PersistenceContext (unitName = "TELCOEJB")
    private EntityManager em;

    public Service findServiceById(int serviceId){
        return em.find(Service.class, serviceId);
    }

    public Service createService(String type, int min, int sms, double feemin, double feesms, int giga, double feegiga){
        Service service;
        if (type.equals("fixedphone")){
            service = new FixedPhone();
        }
        else if (type.equals("mobilephone")){
            service = new MobilePhone(min, sms, feemin, feesms);
        }
        else if (type.equals("fixedinternet")){
            service = new FixedInternet(giga, feegiga);
        }
        else {
            service = new MobileInternet(giga, feegiga);
        }
        em.persist(service);
        return service;
    }



}
