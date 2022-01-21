package ua.com.alevel.view.dto.size;

import ua.com.alevel.view.dto.RequestDto;

public class SizeRequestDto extends RequestDto {

    private Long id;
    private Integer size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
