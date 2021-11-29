package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.Order;
import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.entities.Subscription;
import it.polimi.db69.telco.telcoejb.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Stateless
public class OrderService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/OrderService")
    private OrderService orderService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/UserService")
    private UserService userService;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ProductService")
    private ProductService productService;


    public Subscription findBySubscriptionById(int subscriptionId){
        return em.find(Subscription.class, subscriptionId);
    }

    public Order findOrderById(int orderId){
        return em.find(Order.class, orderId);
    }

    public Order createOrder(Timestamp datetime, int userId, int subscriptionId){
        User user = userService.findUserById(userId);
        Subscription subscription = findBySubscriptionById(subscriptionId);
        Order order = new Order(datetime, user, subscription);
        em.persist(order);
        return order;
    }

    public void createSubscription(Subscription subscription){
        em.persist(subscription);
    }

    public Subscription addProductToSubscription(int productId, int subscriptionId){
        Subscription subscription = findBySubscriptionById(subscriptionId);
        Product product = productService.findProductById(productId);

        if(!subscription.getSubscriptionProducts().contains(product)){
            subscription.addProduct(product);
        }

        return subscription;
    }




}
