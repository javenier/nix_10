package ua.com.alevel.view.dto.model;

import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.view.dto.ResponseDto;

public class ModelResponseDto extends ResponseDto {

    private String name;
    private String brand;

    public ModelResponseDto(Model model) {
        super.setId(model.getId());
        super.setCreated(model.getCreated());
        super.setUpdated(model.getUpdated());
        this.name = model.getName();
        this.brand = model.getBrand().getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
