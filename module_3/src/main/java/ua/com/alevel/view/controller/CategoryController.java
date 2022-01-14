package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.CategoryFacade;
import ua.com.alevel.view.dto.category.CategoryRequestDto;
import ua.com.alevel.view.dto.category.CategoryResponseDto;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryFacade categoryFacade;

    public CategoryController(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    @GetMapping
    public String all(Model model) {
        List<CategoryResponseDto> categories = categoryFacade.findAll();
        model.addAttribute("categories", categories);
        return "pages/category/category_all";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        CategoryResponseDto category = categoryFacade.findById(id);
        model.addAttribute("category", category);
        return "pages/category/category_details";
    }

    @GetMapping("/new")
    public String redirectToNewCategoryPage(Model model) {
        model.addAttribute("category", new CategoryRequestDto());
        return "pages/category/category_new";
    }

    @PostMapping("/new")
    public String createNewCategory(@ModelAttribute("category") CategoryRequestDto dto) {
        categoryFacade.create(dto);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryFacade.delete(id);
        return "redirect:/categories";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        CategoryResponseDto resDto = categoryFacade.findById(id);
        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setId(id);
        model.addAttribute("categoryName", resDto.getName());
        model.addAttribute("category", dto);
        return "pages/category/category_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("category") CategoryRequestDto dto) {
        categoryFacade.update(dto);
        return "redirect:/categories";
    }
}