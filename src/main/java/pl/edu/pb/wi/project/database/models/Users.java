package pl.edu.pb.wi.project.database.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Users")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "finalize_exchange",
                procedureName = "utilities.finalize_exchange",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "offer_id", type = Long.class)
                })
})
public class Users implements Serializable{
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Id
    @SequenceGenerator(name="users_seq", sequenceName="users_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="users_seq")
    Long id;

    @Column(name = "NAME")

    String name;

    @Column(name = "LAST_NAME")
    String lastName;

    @Column(name = "LOGIN")
    String login;

    @Column(name = "USER_PASSWORD")
    String userPassword;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "CITY")
    String city;

    @Column(name = "PREMIUM_USER")
    Boolean premiumUser;

    @Column(name = "ADMIN")
    Boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isPremiumUser() {
        return premiumUser;
    }

    public void setPremiumUser(boolean premiumUser) {
        this.premiumUser = premiumUser;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
