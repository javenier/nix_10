package ua.com.alevel.view.dto.cart;

import ua.com.alevel.view.dto.RequestDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;

public class CartItemRequestDto extends RequestDto {

    private Long sizeId;
    private Long sneakerId;

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getSneakerId() {
        return sneakerId;
    }

    public void setSneakerId(Long sneakerId) {
        this.sneakerId = sneakerId;
    }
}
