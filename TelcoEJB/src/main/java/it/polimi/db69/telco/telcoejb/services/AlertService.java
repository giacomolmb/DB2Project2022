package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.Alert;
import it.polimi.db69.telco.telcoejb.entities.LoginLog;
import it.polimi.db69.telco.telcoejb.entities.User;


import javax.ejb.Stateless;
import javax.persistence.*;
import javax.security.auth.login.CredentialException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Stateless
public class AlertService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public AlertService(){}

    public Collection<Alert> getAlerts(){
        return em.createNamedQuery("Alert.getAll", Alert.class).getResultList();
    }
}

