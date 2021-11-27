package it.polimi.db69.telco.telcoejb.entities;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.text.DecimalFormat;

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

    public MobilePhone() {
    }

    public MobilePhone(int minutes, int sms, double minutesfee, double smsfee) {
        this.minutes = minutes;
        this.sms = sms;
        this.minutesfee = minutesfee;
        this.smsfee = smsfee;
    }

    @Override
    public String printService() {
        return "Mobile Phone: " + minutes + " minutes, " + sms + " SMSs";
    }

    @Override
    public String printExtraFees() {
        return String.format("%.2f", minutesfee) + "$ for each extra minute, " + String.format("%.2f", smsfee) + "$ for each extra SMS";
    }
}
