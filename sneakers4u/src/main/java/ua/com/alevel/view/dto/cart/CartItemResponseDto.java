package ua.com.alevel.view.dto.cart;

import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.ResponseDto;

public class CartItemResponseDto extends ResponseDto {

    private String imageUrl;
    private String brand;
    private String modelAndVersion;
    private Integer size;
    private String price;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelAndVersion() {
        return modelAndVersion;
    }

    public void setModelAndVersion(String modelAndVersion) {
        this.modelAndVersion = modelAndVersion;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
