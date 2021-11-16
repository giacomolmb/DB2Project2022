package it.polimi.db69.telco.telcoejb.entities;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("mobileinternet")
public class MobileInternet extends Service{

    @Column(name = "giga", nullable = false)
    private int giga;

    @Column(name = "gigafee", nullable = false)
    private double gigafee;

    @Override
    public String printService() {
        return "Mobile Internet: " + giga + " Gigabytes";
    }

    @Override
    public String printExtraFees() {
        return gigafee + " for each extra GB";
    }
}
