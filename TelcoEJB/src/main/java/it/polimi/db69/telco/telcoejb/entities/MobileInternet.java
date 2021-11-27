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

    public MobileInternet(int giga, double gigafee) {
        this.giga = giga;
        this.gigafee = gigafee;
    }

    public MobileInternet() {
    }

    @Override
    public String printService() {
        return "Mobile Internet: " + giga + " Gigabytes";
    }

    @Override
    public String printExtraFees() {
        return String.format("%.2f", gigafee) + "$ for each extra GB";
    }
}
