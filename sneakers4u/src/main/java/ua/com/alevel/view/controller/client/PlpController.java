package ua.com.alevel.view.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BrandFacade;
import ua.com.alevel.facade.SneakerFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.brand.BrandResponseDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

@Controller
@RequestMapping("/catalogue")
public class PlpController extends BaseController {

    private final SneakerFacade sneakerFacade;
    private final BrandFacade brandFacade;

    public PlpController(SneakerFacade sneakerFacade, BrandFacade brandFacade) {
        this.sneakerFacade = sneakerFacade;
        this.brandFacade = brandFacade;
    }

    @GetMapping
    public String all(Model model, WebRequest request, @RequestParam(required = false) Long brandId) {
        PageData<SneakerResponseDto> sneakers;
        if(brandId != null) {
            sneakers = sneakerFacade.findAllByBrandId(request, brandId);
            model.addAttribute("catalogueHeader", brandFacade.findById(brandId).getName());
        } else {
            sneakers = sneakerFacade.findAll(request);
            model.addAttribute("catalogueHeader", "All sneakers");
        }
        model.addAttribute("sneakers", sneakers.getItems());
        return "pages/plp/plp";
    }

    @GetMapping("/men")
    public String menCatalogue(Model model, WebRequest request) {
        PageData<SneakerResponseDto> sneakers = sneakerFacade.findAllByGender(request, "'MALE'");
        model.addAttribute("sneakers", sneakers.getItems());
        model.addAttribute("catalogueHeader", "Sneakers for men");
        return "pages/plp/plp";
    }

    @GetMapping("/women")
    public String womenCatalogue(Model model, WebRequest request) {
        PageData<SneakerResponseDto> sneakers = sneakerFacade.findAllByGender(request, "'FEMALE'");
        model.addAttribute("sneakers", sneakers.getItems());
        model.addAttribute("catalogueHeader", "Sneakers for women");
        return "pages/plp/plp";
    }

    @GetMapping("/brands")
    public String brands(Model model, WebRequest request) {
        PageData<BrandResponseDto> pageData = brandFacade.findAll(request);
        model.addAttribute("brands", pageData.getItems());
        return "pages/plp/brands";
    }
}