package ua.com.alevel.view.dto.account;

import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.type.AccountType;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.ResponseDto;

public class AccountResponseDto extends ResponseDto {

    private Long userId;
    private AccountType accountType;
    private String balance;

    public AccountResponseDto(Account account) {
        super.setId(account.getId());
        this.accountType = account.getAccountType();
        this.balance = MoneyConverterUtil.pennyToString(account.getBalance());
        this.userId = account.getUser().getId();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
