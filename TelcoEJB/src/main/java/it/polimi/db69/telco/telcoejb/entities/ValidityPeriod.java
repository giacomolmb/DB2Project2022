package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "validityperiod")
public class ValidityPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column (name = "fee", nullable = false)
    private int fee;

    @Column(name = "months")
    private int months;

    @ManyToOne
    @JoinColumn(name = "packageid")
    private ServicePackage vpServicePackage;

    @OneToMany(mappedBy = "subValidityPeriod")
    private Collection<Subscription> subscriptions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public ServicePackage getVpPackage() {
        return vpServicePackage;
    }

    public void setVpPackage(ServicePackage vpServicePackage) {
        this.vpServicePackage = vpServicePackage;
    }

    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
