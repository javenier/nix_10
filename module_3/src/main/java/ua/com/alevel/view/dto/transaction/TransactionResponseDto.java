package ua.com.alevel.view.dto.transaction;

import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.ResponseDto;

import java.util.Date;

public class TransactionResponseDto extends ResponseDto {

    private Long userId;
    private Long accountId;
    private String amount;
    private String categoryName;
    private Date created;

    public TransactionResponseDto(Transaction transaction) {
        super.setId(transaction.getId());
        this.created = transaction.getCreated();
        this.amount = MoneyConverterUtil.pennyToString(transaction.getAmount());
        this.accountId = transaction.getAccount().getId();
        this.userId = transaction.getAccount().getUser().getId();
        this.categoryName = transaction.getCategory().getName();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
