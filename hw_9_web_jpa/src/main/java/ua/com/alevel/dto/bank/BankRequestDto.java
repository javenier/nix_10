package ua.com.alevel.dto.bank;

import ua.com.alevel.dto.RequestDto;
import ua.com.alevel.type.BankType;

public class BankRequestDto extends RequestDto {

    private Long id;
    private String name;
    private Integer yearOfFoundation;
    private BankType bankType;

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
