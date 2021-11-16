package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.Employee;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "TELCOEJB")
    EntityManager em;

    public EmployeeService(){}

    public Employee checkCredentials(String username, String password){
        List<Employee> uList = null;
        uList = em.createNamedQuery("Employee.checkEmployeeCredentials", Employee.class)
                .setParameter("username", username).setParameter("password", password).getResultList();
        if(uList.isEmpty()) return null;
        else return uList.get(0);
    }
}
