package ua.com.alevel.view.dto.size;

import ua.com.alevel.persistence.entity.item.attributes.Size;
import ua.com.alevel.view.dto.ResponseDto;

public class SizeResponseDto extends ResponseDto {

    private Integer size;

    public SizeResponseDto(Size size) {
        super.setId(size.getId());
        super.setCreated(size.getCreated());
        super.setUpdated(size.getUpdated());
        this.size = size.getSize();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
