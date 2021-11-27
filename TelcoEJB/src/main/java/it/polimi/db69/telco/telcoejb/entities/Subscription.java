package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.sql.Date;

@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "startdate", nullable = false)
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "vpid")
    private ValidityPeriod subValidityPeriod;

    @OneToOne(mappedBy = "orderSubscription")
    private Order subOrder;

    @ManyToMany
    @JoinTable(name="subscriptionproduct",
            joinColumns=@JoinColumn(name = "subscriptionid"),
            inverseJoinColumns=@JoinColumn(name="productid"))
    private Collection<Product> subscriptionProducts;

    public Subscription() {
    }

    public Subscription(Date startDate, ValidityPeriod subValidityPeriod) {
        this.startDate = startDate;
        this.subValidityPeriod = subValidityPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ValidityPeriod getSubValidityPeriod() {
        return subValidityPeriod;
    }

    public void setSubValidityPeriod(ValidityPeriod subValidityPeriod) {
        this.subValidityPeriod = subValidityPeriod;
    }

    public Collection<Product> getSubscriptionProducts() {
        return subscriptionProducts;
    }

    public void setSubscriptionProducts(Collection<Product> subscriptionProducts) {
        this.subscriptionProducts = subscriptionProducts;
    }

    public void addProduct(Product product){
        this.getSubscriptionProducts().add(product);
        product.getProductSubscriptions().add(this);
    }

    public String calculateTotalPrice(){
        String totalPrice;
        double price = subValidityPeriod.getFee()* subValidityPeriod.getMonths();
        if (subscriptionProducts != null) {
            for (Product product : subscriptionProducts) {
                price += product.getFee() * subValidityPeriod.getMonths();
            }
        }
        totalPrice = "Total price: " + price + "$";
        return totalPrice;
    }

    //todo start date - expiration date

}
