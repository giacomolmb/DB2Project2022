package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

import java.util.Collection;

@Entity
@Table(name="package")
@NamedQueries({
        @NamedQuery(name = "package.findByName", query = "SELECT p FROM ServicePackage p WHERE p.name = :name"),
        @NamedQuery(name = "package.getAll", query = "SELECT p FROM ServicePackage p")
})

public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="name", nullable = false, length = 45)
    private String name;

    @OneToMany(mappedBy = "vpServicePackage")
    private Collection<ValidityPeriod> packageValidityPeriods;

    @OneToMany(mappedBy = "servicePackage")
    private Collection<Service> services;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="packageproduct",
            joinColumns=@JoinColumn(name="packageid"),
            inverseJoinColumns=@JoinColumn(name="productid"))
    private Collection<Product> products;


    public ServicePackage() {
    }

    public ServicePackage(String name) {
        this.name = name;
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

    public Collection<ValidityPeriod> getPackageValidityPeriods() {
        return packageValidityPeriods;
    }

    public void setPackageValidityPeriods(Collection<ValidityPeriod> packageValidityPeriods) {
        this.packageValidityPeriods = packageValidityPeriods;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        this.getProducts().add(product);
        product.getPackages().add(this);
    }

    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }
}
