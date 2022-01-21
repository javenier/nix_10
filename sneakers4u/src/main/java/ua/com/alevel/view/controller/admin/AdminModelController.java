package ua.com.alevel.view.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.ModelFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.brand.BrandRequestDto;
import ua.com.alevel.view.dto.brand.BrandResponseDto;
import ua.com.alevel.view.dto.model.ModelRequestDto;
import ua.com.alevel.view.dto.model.ModelResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

@Controller
@RequestMapping("/admin/models")
public class AdminModelController extends BaseController {

    private final ModelFacade modelFacade;

    public AdminModelController(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    private final HeaderName[] columnNamesForFindAll = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("brand", "brand", "brand_id"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    @GetMapping
    public String allModels(Model model, WebRequest request, @RequestParam(required = false) Long brandId) {
        PageData<ModelResponseDto> pageData;
        if(brandId != null) {
            pageData = modelFacade.findAllByBrandId(request, brandId);
        } else {
            pageData = modelFacade.findAll(request);
        }
        initDataTable(pageData, columnNamesForFindAll, model);
        model.addAttribute("pageData", pageData);
        model.addAttribute("createUrl", "models/all");
        model.addAttribute("cardHeader", "All models");
        model.addAttribute("createNew", "models/new");
        model.addAttribute("buttonVisible", false);
        return "pages/admin/models/models_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/models");
    }

    @GetMapping("/new")
    public String redirectToNewModelPage(Model model, @RequestParam Long brandId) {
        ModelRequestDto modelRequestDto = new ModelRequestDto();
        modelRequestDto.setBrandId(brandId);
        model.addAttribute("model", modelRequestDto);
        return "pages/admin/models/model_new";
    }

    @PostMapping("/new")
    public String createNewModel(@ModelAttribute("model") ModelRequestDto dto) {
        modelFacade.create(dto);
        return "redirect:/admin/models";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        modelFacade.delete(id);
        return "redirect:/admin/models";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        ModelResponseDto resDto = modelFacade.findById(id);
        ModelRequestDto dto = new ModelRequestDto();
        dto.setId(id);
        model.addAttribute("modelName", resDto.getName());
        model.addAttribute("model", dto);
        return "pages/admin/models/model_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("model") ModelRequestDto model) {
        modelFacade.update(model);
        return "redirect:/admin/models";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        ModelResponseDto dto = modelFacade.findById(id);
        model.addAttribute("model", dto);
        return "pages/admin/models/model_details";
    }
}
