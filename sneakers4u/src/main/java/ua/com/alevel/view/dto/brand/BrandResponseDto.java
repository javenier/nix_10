package ua.com.alevel.view.dto.brand;

import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.view.dto.ResponseDto;

public class BrandResponseDto extends ResponseDto {

    private String name;
    private String imageUrl;

    public BrandResponseDto(Brand brand) {
        super.setId(brand.getId());
        super.setCreated(brand.getCreated());
        super.setUpdated(brand.getUpdated());
        this.name = brand.getName();
        this.imageUrl = brand.getImageUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
