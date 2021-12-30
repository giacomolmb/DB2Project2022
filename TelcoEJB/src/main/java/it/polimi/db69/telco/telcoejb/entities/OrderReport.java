package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

@Entity
@Table(name = "salesreport")
@NamedQueries({
        @NamedQuery(name = "OrderReport.getRejected", query = "SELECT o FROM OrderReport o WHERE o.orderStatus = \"REJECTED\""),
        @NamedQuery(name = "OrderReport.getRejectedByUser", query = "SELECT o FROM OrderReport o WHERE o.orderStatus = \"REJECTED\" AND o.user = :user")
})
public class OrderReport {
    @Id
    @OneToOne
    @JoinColumn(name = "orderid")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @OneToOne
    @JoinColumn(name = "subid")
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "vpid")
    private ValidityPeriod vp;

    @Column(name = "base_amount")
    private double baseAmount;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "num_of_products")
    private int numProducts;

    @Column(name = "order_status")
    private String orderStatus;

    public Order getOrder() {
        return order;
    }

    public User getUser() {
        return user;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public ValidityPeriod getVp() {
        return vp;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getNumProducts() {
        return numProducts;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
