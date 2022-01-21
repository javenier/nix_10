package ua.com.alevel.view.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.BrandFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.brand.BrandRequestDto;
import ua.com.alevel.view.dto.brand.BrandResponseDto;
import ua.com.alevel.view.dto.size.SizeRequestDto;
import ua.com.alevel.view.dto.webrequest.PageData;

@Controller
@RequestMapping("/admin/brands")
public class AdminBrandController extends BaseController {

    private final BrandFacade brandFacade;

    public AdminBrandController(BrandFacade brandFacade) {
        this.brandFacade = brandFacade;
    }

    private final HeaderName[] columnNamesForFindAll = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    @GetMapping
    public String allBrands(Model model, WebRequest request) {
        PageData<BrandResponseDto> pageData = brandFacade.findAll(request);
        initDataTable(pageData, columnNamesForFindAll, model);
        model.addAttribute("pageData", pageData);
        model.addAttribute("createUrl", "brands/all");
        model.addAttribute("cardHeader", "All brands");
        model.addAttribute("createNew", "brands/new");
        model.addAttribute("buttonVisible", true);
        return "pages/admin/brands/brands_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/brands");
    }

    @GetMapping("/new")
    public String redirectToNewBrandPage(Model model) {
        model.addAttribute("brand", new BrandRequestDto());
        return "pages/admin/brands/brand_new";
    }

    @PostMapping("/new")
    public String createNewBrand(@ModelAttribute("brand") BrandRequestDto dto) {
        brandFacade.create(dto);
        return "redirect:/admin/brands";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        brandFacade.delete(id);
        return "redirect:/admin/brands";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        BrandResponseDto resDto = brandFacade.findById(id);
        BrandRequestDto dto = new BrandRequestDto();
        dto.setId(id);
        model.addAttribute("brandName", resDto.getName());
        model.addAttribute("brand", dto);
        return "pages/admin/brands/brand_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("brand") BrandRequestDto brand) {
        brandFacade.update(brand);
        return "redirect:/admin/brands";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        BrandResponseDto dto = brandFacade.findById(id);
        model.addAttribute("brand", dto);
        return "pages/admin/brands/brand_details";
    }
}
