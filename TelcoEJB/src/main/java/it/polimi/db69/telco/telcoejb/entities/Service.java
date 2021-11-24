package it.polimi.db69.telco.telcoejb.entities;

import it.polimi.db69.telco.telcoejb.enums.ServiceType;

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

    @ManyToOne
    @JoinColumn(name="packageid")
    private ServicePackage servicePackage;

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public abstract String printService();

    public abstract String printExtraFees();
}
