package ua.com.alevel.view.dto.order;

import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.ResponseDto;

public class OrderResponseDto extends ResponseDto {

    private String clientEmail;
    private String clientFullName;
    private String totalPrice;
    private String comment;
    private String address;
    private Integer postOffice;
    private boolean finished;

    public OrderResponseDto(Order order) {
        super.setId(order.getId());
        super.setCreated(order.getCreated());
        super.setUpdated(order.getUpdated());
        this.clientEmail = order.getClient().getEmail();
        this.clientFullName = order.getClient().getFirstName() + " " + order.getClient().getLastName();
        this.totalPrice = MoneyConverterUtil.pennyToString(order.getTotalPrice());
        this.comment = order.getComment();
        this.address = order.getAddress();
        this.postOffice = order.getPostOffice();
        this.finished = order.isFinished();
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
