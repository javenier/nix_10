package ua.com.alevel.view.dto.link;

import ua.com.alevel.view.dto.RequestDto;

public class SneakerSizeLinkRequestDto extends RequestDto {

    private Long sneakerId;
    private Long sizeId;

    public Long getSneakerId() {
        return sneakerId;
    }

    public void setSneakerId(Long sneakerId) {
        this.sneakerId = sneakerId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }
}
