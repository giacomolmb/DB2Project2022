package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "startdate", nullable = false)
    private Timestamp startDate;

    @Column(name="vpid", nullable = false, insertable=false, updatable=false)
    private int vpId;

    @Column(name="orderid", nullable = false, insertable=false, updatable=false)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "vpid")
    private ValidityPeriod subValidityPeriod;

    @OneToOne
    @JoinColumn(name = "orderid")
    private Order subOrder;

    @ManyToMany
    @JoinTable(name="subscriptionproduct",
            joinColumns=@JoinColumn(name = "subscriptionid"),
            inverseJoinColumns=@JoinColumn(name="productid"))
    private Collection<Product> subscriptionProducts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public ValidityPeriod getSubValidityPeriod() {
        return subValidityPeriod;
    }

    public void setSubValidityPeriod(ValidityPeriod subValidityPeriod) {
        this.subValidityPeriod = subValidityPeriod;
    }

    public Order getSubOrder() {
        return subOrder;
    }

    public void setSubOrder(Order subOrder) {
        this.subOrder = subOrder;
    }
}
