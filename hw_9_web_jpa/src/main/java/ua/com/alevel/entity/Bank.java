package ua.com.alevel.entity;

import ua.com.alevel.type.BankType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "banks")
public class Bank extends BaseEntity {

    private String name;

    @Column(name = "year_of_foundation")
    private Integer yearOfFoundation;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_type")
    private BankType bankType;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "bank_client", joinColumns = @JoinColumn(name = "bank_id"),
    inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<Client> clients;

    public Bank() {
        super();
        this.clients = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearOfFoundation() {
        return yearOfFoundation;
    }

    public void setYearOfFoundation(Integer yearOfFoundation) {
        this.yearOfFoundation = yearOfFoundation;
    }

    public BankType getBankType() {
        return bankType;
    }

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        clients.add(client);
        client.getBanks().add(this);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        client.getBanks().remove(this);
    }
}