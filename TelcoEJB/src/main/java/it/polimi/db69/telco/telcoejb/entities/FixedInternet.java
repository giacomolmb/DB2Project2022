package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("fixedinternet")
public class FixedInternet extends Service{

    @Column(name = "giga", nullable = false)
    private int giga;

    @Column(name = "gigafee", nullable = false)
    private double gigafee;

    public FixedInternet(int giga, double gigafee) {
        this.giga = giga;
        this.gigafee = gigafee;
    }

    public FixedInternet() {
    }

    @Override
    public String printService() {
        return "Fixed Internet: " + giga + " Gigabytes";
    }

    @Override
    public String printExtraFees() {
        return String.format("%.2f", gigafee) + "$ for each extra GB";
    }
}
