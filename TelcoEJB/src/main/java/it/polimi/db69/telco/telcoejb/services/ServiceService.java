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

    public Collection<Service> findServiceByType(ServiceType type){
        List<Service> services;
        services = em.createNamedQuery( "service.getByType", Service.class)
                .setParameter("type", type).getResultList();
        //se non esiste nessun servizio di questo tipo ??
        return services;
    }

    public Service createService(ServiceType type, int min, int sms, double feemin, double feesms, int giga, double feegiga){
        Service service;
        if (type.equals(ServiceType.fixedphone)){
            service = new FixedPhone();
        }
        else if (type.equals(ServiceType.mobilephone)){
            service = new MobilePhone(min, sms, feemin, feesms);
        }
        else if (type.equals(ServiceType.fixedinternet)){
            service = new FixedInternet(giga, feegiga);
        }
        else {
            service = new MobileInternet(giga, feegiga);
        }
        em.persist(service);
        return service;
    }



}
