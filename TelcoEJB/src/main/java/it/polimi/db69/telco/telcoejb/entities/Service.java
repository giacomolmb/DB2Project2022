package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name ="service")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @ManyToMany
    @JoinTable(name="packageservice",
            joinColumns=@JoinColumn(name="serviceid"),
            inverseJoinColumns=@JoinColumn(name="packageid"))
    private Collection<ServicePackage> servicePackages;

    public abstract String printService();

    public abstract String printExtraFees();

}
