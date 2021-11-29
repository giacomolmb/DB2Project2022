package it.polimi.db69.telco.telcoejb.entities;

import it.polimi.db69.telco.telcoejb.enums.OrderStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "costumer_order")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "datetime", nullable = false)
    private Timestamp datetime;

    @Column (name = "status", nullable = false, length = 45)
    private String status;

    @OneToOne
    @JoinColumn(name = "subscriptionid")
    private Subscription orderSubscription;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User orderUser;

    public Order() {
    }

    public Order(Timestamp datetime, User orderUser, Subscription orderSubscription) {
        this.datetime = datetime;
        this.status = "pending";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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