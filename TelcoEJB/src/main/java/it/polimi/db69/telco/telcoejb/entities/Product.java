package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="product")
@NamedQueries({
        @NamedQuery(name = "Product.getProductByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
        @NamedQuery(name = "product.findAll", query ="SELECT p FROM Product p")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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

    public Product(String name, double fee) {
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
