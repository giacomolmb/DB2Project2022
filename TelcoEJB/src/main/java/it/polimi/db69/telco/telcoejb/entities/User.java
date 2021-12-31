package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="user")
@NamedQueries({
        @NamedQuery(name = "User.checkCredentials", query = "SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2"),
        @NamedQuery(name = "User.getAllUsers", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.getInsolventUsers", query = "SELECT u FROM User u WHERE u.insolvent = 1"),
        @NamedQuery(name = "User.getFromEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.getFromUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="username", nullable = false, length = 45)
    private String username;

    @Column(name="password", nullable = false, length = 45)
    private String password;

    @Column(name="email", nullable = false, length = 45)
    private String email;

    @Column(name="insolvent", nullable = false)
    private boolean insolvent;

    @OneToMany(mappedBy = "alertUser")
    private Collection<Alert> userAlerts;

    @OneToMany(mappedBy = "orderUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Order> userOrders;

    @OneToMany(mappedBy = "loggedUser", cascade = CascadeType.PERSIST)
    private Collection<LoginLog> userLogins;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.insolvent = false;
    }

    public User(){}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInsolvent() {
        return insolvent;
    }

    public void setInsolvent(boolean insolvent) {
        this.insolvent = insolvent;
    }

    public Collection<Alert> getUserAlerts() {
        return userAlerts;
    }

    public void setUserAlerts(Collection<Alert> userAlerts) {
        this.userAlerts = userAlerts;
    }

    public Collection<Order> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(Collection<Order> userOrders) {
        this.userOrders = userOrders;
    }

    public Collection<LoginLog> getUserLogins() {
        return userLogins;
    }

    public void setUserLogins(Collection<LoginLog> userLogins) {
        this.userLogins = userLogins;
    }

    public void addUserLogin(LoginLog loginLog){
        this.userLogins.add(loginLog);
        loginLog.setLoggedUser(this);
    }

    public Collection<Order> getActiveOrders(){
        ArrayList<Order> activeOrders = new ArrayList<>();
        if (!userOrders.isEmpty()) {
            for (Order order : userOrders) {
                if (order.getStatus().equals("ACCEPTED"))
                    activeOrders.add(order);
            }
        }

        if (activeOrders.isEmpty()) return null;
        else return activeOrders;
    }
}
