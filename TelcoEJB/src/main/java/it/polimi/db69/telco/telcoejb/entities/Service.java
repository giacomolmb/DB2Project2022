package it.polimi.db69.telco.telcoejb.entities;

import it.polimi.db69.telco.telcoejb.enums.ServiceType;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name ="service")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@NamedQueries({
        @NamedQuery(name = "service.getByType", query = "SELECT s FROM Service s WHERE s.type = :type")
})
public abstract class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="type", nullable = false)
    private ServiceType type;

    @ManyToMany
    @JoinTable(name="packageservice",
            joinColumns=@JoinColumn(name="serviceid"),
            inverseJoinColumns=@JoinColumn(name="packageid"))
    private Collection<ServicePackage> servicePackages;



    public abstract String printService();

    public abstract String printExtraFees();

}
