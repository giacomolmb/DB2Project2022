package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false, length = 45)
    private String name;

    @Column(name="fee", nullable = false)
    private double fee;

    @ManyToMany(mappedBy = "products")
    private Collection<ServicePackage> servicePackages;

    @ManyToMany(mappedBy = "subscriptionProducts")
    private Collection<Subscription> productSubscriptions;

    public Product() {
    }

    public Product(int id, String name, double fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Collection<ServicePackage> getPackages() {
        return servicePackages;
    }

    public void setPackages(Collection<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
    }
}
