package it.polimi.db69.telco.telcoejb.entities;

import it.polimi.db69.telco.telcoejb.enums.OrderStatus;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "datetime", nullable = false)
    private Timestamp datetime;

    @Column (name = "status", nullable = false, length = 45)
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "subscriptionid")
    private Subscription orderSubscription;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User orderUser;

    public Order() {
    }

    public Order(int id, Timestamp datetime, OrderStatus status, User orderUser, Subscription orderSubscription) {
        this.id = id;
        this.datetime = datetime;
        this.status = status;
        this.orderUser = orderUser;
        this.orderSubscription = orderSubscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }

    public Subscription getOrderSubscription() {
        return orderSubscription;
    }

    public void setOrderSubscription(Subscription orderSubscription) {
        this.orderSubscription = orderSubscription;
    }
}