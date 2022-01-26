package ua.com.alevel.view.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.BrandFacade;
import ua.com.alevel.facade.SneakerFacade;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.brand.BrandResponseDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

import java.util.ArrayList;
import java.util.List;

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
        if (brandId != null) {
            sneakers = sneakerFacade.findAllByBrandId(request, brandId);
            model.addAttribute("catalogueHeader", brandFacade.findById(brandId).getName());
        } else {
            sneakers = sneakerFacade.findAll(request);
            model.addAttribute("catalogueHeader", "All sneakers");
        }
        long totalPageSize = 1;
        if(sneakers.getItemsSize() % WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE == 0)
            totalPageSize = sneakers.getItemsSize() / WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE;
        else
            totalPageSize = sneakers.getItemsSize() / WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE + 1;
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < totalPageSize; i++) {
            pages.add(i + 1);
        }
        model.addAttribute("pages", pages);
        model.addAttribute("pageData", sneakers);
        model.addAttribute("createUrl", "catalogue");
        return "pages/plp/plp";
    }

    @PostMapping
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "catalogue");
    }

    @GetMapping("/men")
    public String menCatalogue(Model model, WebRequest request) {
        PageData<SneakerResponseDto> sneakers = sneakerFacade.findAllByGender(request, "'MALE'");
        model.addAttribute("catalogueHeader", "Sneakers for men");
        long totalPageSize = 1;
        if(sneakers.getItemsSize() % WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE == 0)
            totalPageSize = sneakers.getItemsSize() / WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE;
        else
            totalPageSize = sneakers.getItemsSize() / WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE + 1;
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < totalPageSize; i++) {
            pages.add(i + 1);
        }
        model.addAttribute("pages", pages);
        model.addAttribute("pageData", sneakers);
        model.addAttribute("createUrl", "men");
        return "pages/plp/plp";
    }

    @PostMapping("/men")
    public ModelAndView findAllRedirectMen(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "catalogue/men");
    }

    @GetMapping("/women")
    public String womenCatalogue(Model model, WebRequest request) {
        PageData<SneakerResponseDto> sneakers = sneakerFacade.findAllByGender(request, "'FEMALE'");
        model.addAttribute("sneakers", sneakers.getItems());
        model.addAttribute("catalogueHeader", "Sneakers for women");
        long totalPageSize = 1;
        if(sneakers.getItemsSize() % WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE == 0)
            totalPageSize = sneakers.getItemsSize() / WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE;
        else
            totalPageSize = sneakers.getItemsSize() / WebRequestUtil.DEFAULT_SIZE_PARAM_VALUE + 1;
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < totalPageSize; i++) {
            pages.add(i + 1);
        }
        model.addAttribute("pages", pages);
        model.addAttribute("pageData", sneakers);
        model.addAttribute("createUrl", "women");
        return "pages/plp/plp";
    }

    @PostMapping("/women")
    public ModelAndView findAllRedirectWomen(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "catalogue/women");
    }

    @GetMapping("/brands")
    public String brands(Model model, WebRequest request) {
        PageData<BrandResponseDto> pageData = brandFacade.findAll(request);
        model.addAttribute("brands", pageData.getItems());
        return "pages/plp/brands";
    }
}