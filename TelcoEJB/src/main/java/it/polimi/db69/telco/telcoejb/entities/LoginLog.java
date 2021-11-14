package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import java.sql.Timestamp;

@Entity
@Table(name  = "login_log")
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column (name="logtime", nullable = false)
    private Timestamp logtime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User loggedUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getLogtime() {
        return logtime;
    }

    public void setLogtime(Timestamp logtime) {
        this.logtime = logtime;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
