package ua.com.alevel.dto;

public class LinkRequestDto extends RequestDto {

    private Long bankId;
    private Long clientId;

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
