package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.CategoryFacade;
import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.service.CategoryService;
import ua.com.alevel.view.dto.category.CategoryRequestDto;
import ua.com.alevel.view.dto.category.CategoryResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;

    public CategoryFacadeImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void create(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setIncome(categoryRequestDto.isIncome());
        categoryService.create(category);
    }

    @Override
    public void update(CategoryRequestDto categoryRequestDto) {
        Category category = categoryService.findById(categoryRequestDto.getId());
        if(category != null) {
            category.setIncome(categoryRequestDto.isIncome());
            category.setName(categoryRequestDto.getName());
            categoryService.update(category);
        }
    }

    @Override
    public void delete(Long id) {
        categoryService.delete(id);
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        return new CategoryResponseDto(categoryService.findById(id));
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        List<Category> categories = categoryService.findAll();
        return categories.
                stream().
                map(CategoryResponseDto::new).
                collect(Collectors.toList());
    }
}
