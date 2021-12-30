package ua.com.alevel.dto.bank;

import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.type.BankType;

public class BankResponseDto extends ResponseDto {

    private String name;
    private Integer yearOfFoundation;
    private BankType bankType;
    private Integer clientCount;

    public BankResponseDto(Bank bank) {
        this.name = bank.getName();
        this.yearOfFoundation = bank.getYearOfFoundation();
        this.bankType = bank.getBankType();
        super.setId(bank.getId());
        super.setCreated(bank.getCreated());
        super.setUpdated(bank.getUpdated());
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

    public Integer getClientCount() {
        return clientCount;
    }

    public void setClientCount(Integer clientCount) {
        this.clientCount = clientCount;
    }
}
