package ua.com.alevel.entity;

import ua.com.alevel.type.BankType;

public class Bank extends BaseEntity {

    private String name;
    private Integer yearOfFoundation;
    private BankType bankType;

    public Bank() {
        super();
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
}