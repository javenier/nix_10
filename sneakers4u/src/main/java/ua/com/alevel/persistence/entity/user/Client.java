package ua.com.alevel.persistence.entity.user;

import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.persistence.type.RoleType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    @Column(name = "activation_code")
    private String activationCode;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Order> orders;

    public Client() {
        super();
        setRoleType(RoleType.ROLE_CLIENT);
        this.orders = new HashSet<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}