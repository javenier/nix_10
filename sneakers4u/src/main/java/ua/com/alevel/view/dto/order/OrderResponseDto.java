package ua.com.alevel.view.dto.order;

import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.ResponseDto;

public class OrderResponseDto extends ResponseDto {

    private Long clientId;
    private String totalPrice;
    private String comment;
    private String address;
    private Integer postOffice;

    public OrderResponseDto(Order order) {
        super.setId(order.getId());
        super.setCreated(order.getCreated());
        super.setUpdated(order.getUpdated());
        this.clientId = order.getClient().getId();
        this.totalPrice = MoneyConverterUtil.pennyToString(order.getTotalPrice());
        this.comment = order.getComment();
        this.address = order.getAddress();
        this.postOffice = order.getPostOffice();
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(Integer postOffice) {
        this.postOffice = postOffice;
    }
}
