package it.polimi.db69.telco.telcoejb.entities;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alert")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "datetime", nullable = false)
    private Timestamp datetime;

    @Column(name = "amount", nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User alertUser;

    public Alert() {
    }

    public Alert(int id, Timestamp datetime, double amount, User alertUser) {
        this.id = id;
        this.datetime = datetime;
        this.amount = amount;
        this.alertUser = alertUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getAlertUser() {
        return alertUser;
    }

    public void setAlertUser(User alertUser) {
        this.alertUser = alertUser;
    }
}

