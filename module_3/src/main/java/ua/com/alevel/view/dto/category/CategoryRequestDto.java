package ua.com.alevel.view.dto.category;

import ua.com.alevel.view.dto.RequestDto;

public class CategoryRequestDto extends RequestDto {

    private Long id;
    private String name;
    private boolean income;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }
}
