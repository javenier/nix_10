package ua.com.alevel.view.dto.sneaker;

import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.view.dto.RequestDto;

public class SneakerRequestDto extends RequestDto {

    private Long id;
    private Long modelId;
    private String versionOfModel;
    private String price;
    private String description;
    private Integer quantity;
    private String imageUrl;
    private Gender sneakerGender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Gender getSneakerGender() {
        return sneakerGender;
    }

    public void setSneakerGender(Gender sneakerGender) {
        this.sneakerGender = sneakerGender;
    }

    public String getVersionOfModel() {
        return versionOfModel;
    }

    public void setVersionOfModel(String versionOfModel) {
        this.versionOfModel = versionOfModel;
    }
}
