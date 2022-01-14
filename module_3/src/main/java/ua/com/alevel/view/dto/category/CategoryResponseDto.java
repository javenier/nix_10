package ua.com.alevel.view.dto.category;

import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.view.dto.ResponseDto;

public class CategoryResponseDto extends ResponseDto {

    private String name;
    private String associate;

    public CategoryResponseDto(Category category) {
        super.setId(category.getId());
        this.name = category.getName();
        if(category.isIncome())
            this.associate = "Income";
        else
            this.associate = "Outcome";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssociate() {
        return associate;
    }

    public void setAssociate(String associate) {
        this.associate = associate;
    }
}
