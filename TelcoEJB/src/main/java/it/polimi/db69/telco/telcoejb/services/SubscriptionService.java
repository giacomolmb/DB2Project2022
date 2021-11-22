package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.Subscription;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SubscriptionService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public Subscription findById(int subscriptionId){
        return em.find(Subscription.class, subscriptionId);
    }

}

