package ua.com.alevel.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;

    private boolean income;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Transaction> transactions;

    public Category() {
        transactions = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setCategory(this);
    }
}
