package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.entities.ServicePackage;
import it.polimi.db69.telco.telcoejb.entities.Subscription;
import it.polimi.db69.telco.telcoejb.entities.ValidityPeriod;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;

@Stateless
public class SubscriptionService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ValidityPeriodService")
    private ValidityPeriodService validityPeriodService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;


    public Subscription findById(int subscriptionId){
        return em.find(Subscription.class, subscriptionId);
    }

    public Subscription createSubscription(Date startDate, int validityPeriodId){
        ValidityPeriod validityPeriod = validityPeriodService.findById(validityPeriodId);

        Subscription subscription = new Subscription(startDate, validityPeriod);

        em.persist(subscription);
        return subscription;
    }

    public Subscription addProductToSubscription(int productId, int subscriptionId){
        Subscription subscription = findById(subscriptionId);
        Product product = productService.findProductById(productId);

        if(!subscription.getSubscriptionProducts().contains(product)){
            subscription.addProduct(product);
        }

        return subscription;
    }

}

