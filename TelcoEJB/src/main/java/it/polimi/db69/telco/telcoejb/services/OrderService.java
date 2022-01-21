package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Stateless
public class OrderService {

    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

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

    public Collection<Order> findRejectedOrders(){
        if (em.createNamedQuery("Order.getRejectedOrders", Order.class).getResultList().isEmpty())
            return null;
        else
            return em.createNamedQuery("Order.getRejectedOrders", Order.class).getResultList();
    }

    public Order createOrder(Timestamp datetime, int userId, int subscriptionId){
        User user = userService.findUserById(userId);
        Subscription subscription = findBySubscriptionById(subscriptionId);
        Order order = new Order(datetime, user, subscription);
        user.addUserOrder(order);
        em.persist(user);
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

    public void confirmOrder(int orderId){
        Order order = em.find(Order.class, orderId);
        order.setStatus("ACCEPTED");
        em.merge(order);

        em.flush();
    }

    public void rejectOrder(int orderId){
        Order order = em.find(Order.class, orderId);
        order.setStatus("REJECTED");
        em.merge(order);

        em.flush();
    }

    public Collection<OrderReport> getRejectedOrders(){
        return em.createNamedQuery("OrderReport.getRejected", OrderReport.class).getResultList();
    }

    public Collection<OrderReport> getRejectedOrdersByUser(String username){
        User user = userService.findUserByUsername(username);
        return em.createNamedQuery("OrderReport.getRejectedByUser", OrderReport.class).setParameter("user", user).getResultList();
    }
}
