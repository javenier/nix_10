package ua.com.alevel.view.dto.account;

import ua.com.alevel.persistence.type.AccountType;
import ua.com.alevel.view.dto.RequestDto;

public class AccountRequestDto extends RequestDto {

    private Long id;
    private Long userId;
    private AccountType accountType;
    private Long balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
