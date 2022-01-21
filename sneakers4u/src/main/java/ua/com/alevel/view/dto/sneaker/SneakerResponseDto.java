package ua.com.alevel.view.dto.sneaker;

import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.ResponseDto;

public class SneakerResponseDto extends ResponseDto {

    private String versionOfModel;
    private String price;
    private String description;
    private Integer quantity;
    private String imageUrl;
    private String sneakerGender;
    private String brand;
    private String model;

    public SneakerResponseDto(Sneaker sneaker) {
        super.setId(sneaker.getId());
        super.setCreated(sneaker.getCreated());
        super.setUpdated(sneaker.getUpdated());
        this.price = MoneyConverterUtil.pennyToString(sneaker.getPrice());
        this.description = sneaker.getDescription();
        this.quantity = sneaker.getQuantity();
        this.imageUrl = sneaker.getImageUrl();
        this.sneakerGender = sneaker.getSneakerGender().name().equals("MALE") ? "For male" : "For female";
        this.versionOfModel = sneaker.getVersionOfModel();
        this.brand = sneaker.getModel().getBrand().getName();
        this.model = sneaker.getModel().getName();
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

    public String getSneakerGender() {
        return sneakerGender;
    }

    public void setSneakerGender(String sneakerGender) {
        this.sneakerGender = sneakerGender;
    }

    public String getVersionOfModel() {
        return versionOfModel;
    }

    public void setVersionOfModel(String versionOfModel) {
        this.versionOfModel = versionOfModel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}