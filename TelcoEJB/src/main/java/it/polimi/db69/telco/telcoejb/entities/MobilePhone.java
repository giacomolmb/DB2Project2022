package it.polimi.db69.telco.telcoejb.entities;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("mobilephone")
public class MobilePhone extends Service{

    @Column(name = "minutes", nullable = false)
    private int minutes;

    @Column(name = "sms", nullable = false)
    private int sms;

    @Column(name = "minutesfee", nullable = false)
    private double minutesfee;

    @Column(name = "smsfee", nullable = false)
    private double smsfee;
}
