package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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

    @ManyToMany(fetch = FetchType.EAGER)
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

    public double calculateTotalPrice(){
        double price = subValidityPeriod.getFee()* subValidityPeriod.getMonths();
        if (subscriptionProducts != null) {
            for (Product product : subscriptionProducts) {
                price += product.getFee() * subValidityPeriod.getMonths();
            }
        }
        return price;
    }


    public String getStringStartDate(){
       DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return (String) dateFormat.format(startDate);
    }


    public String getExpirationDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.MONTH, subValidityPeriod.getMonths());
        Date expirationDate = new Date(c.getTimeInMillis());
        String strDate = dateFormat.format(expirationDate);
        return strDate;
    }


}
