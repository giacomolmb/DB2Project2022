package it.polimi.db69.telco.telcoejb.entities;


import javax.persistence.*;

@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.checkEmployeeCredentials",
        query = "select e " +
                "from Employee e " +
                "where e.username =: username and e.password =:password")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="username", nullable = false, length = 45)
    private String username;

    @Column(name="password", nullable = false, length = 45)
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
