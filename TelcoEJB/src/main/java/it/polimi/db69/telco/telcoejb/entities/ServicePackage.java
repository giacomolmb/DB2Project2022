package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

import java.util.Collection;

@Entity
@Table(name="package")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="name", nullable = false, length = 45)
    private String name;

    @OneToMany(mappedBy = "vpServicePackage")
    private Collection<ValidityPeriod> packageValidityPeriods;

    @ManyToMany
    @JoinTable(name="packageproduct",
            joinColumns=@JoinColumn(name="productid"),
            inverseJoinColumns=@JoinColumn(name="packageid"))
    private Collection<Product> products;

    @ManyToMany
    @JoinTable(name="packageservice",
            joinColumns=@JoinColumn(name="packageid"),
            inverseJoinColumns=@JoinColumn(name="serviceid"))
    private Collection<Service> services;



    public ServicePackage() {
    }

    public ServicePackage(int id, String name) {
        this.id = id;
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

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
