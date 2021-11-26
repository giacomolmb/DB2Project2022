package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Stateless
public class ValidityPeriodService {
    @PersistenceContext (unitName = "TELCOEJB")
    private EntityManager em;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServicePackageService")
    private ServicePackageService packageService;

    public ValidityPeriod findById(int validityPeriodId){
        return em.find(ValidityPeriod.class, validityPeriodId);
    }

    public ValidityPeriod createValidityPeriod(int months, double fee, int packageId){
        ValidityPeriod vp = new ValidityPeriod(months, fee);
        vp.setVpPackage(packageService.findPackageById(packageId));
        em.persist(vp);

        return vp;
    }
}
