package ua.com.alevel.view.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.SneakerFacade;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.sneaker.SneakerRequestDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

@Controller
@RequestMapping("/admin/sneakers")
public class AdminSneakerController extends BaseController {

    private final SneakerFacade sneakerFacade;

    private final HeaderName[] columnNamesForFindAll = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("brand", "brand", null),
            new HeaderName("model", "model", null),
            new HeaderName("version", "versionOfModel", "version_of_model"),
            new HeaderName("price", "price", "price"),
            new HeaderName("quantity", "quantity", "quantity"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    public AdminSneakerController(SneakerFacade sneakerFacade) {
        this.sneakerFacade = sneakerFacade;
    }

    @GetMapping
    public String allSneakers(Model model, WebRequest request,
                              @RequestParam(required = false) Long brandId,
                              @RequestParam(required = false) Long modelId,
                              @RequestParam(required = false) Long orderId) {
        PageData<SneakerResponseDto> pageData;
        if (brandId != null) {
            pageData = sneakerFacade.findAllByBrandId(request, brandId);
        } else if (modelId != null) {
            pageData = sneakerFacade.findAllByModelId(request, modelId);
        } else if (orderId != null) {
            pageData = sneakerFacade.findAllByOrderId(request, orderId);
        } else {
            pageData = sneakerFacade.findAll(request);
        }
        initDataTable(pageData, columnNamesForFindAll, model);
        model.addAttribute("pageData", pageData);
        model.addAttribute("createUrl", "sneakers/all");
        model.addAttribute("cardHeader", "All sneakers");
        model.addAttribute("createNew", "models/new");
        model.addAttribute("buttonVisible", false);
        return "pages/admin/sneakers/sneakers_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/sneakers");
    }

    @GetMapping("/new")
    public String redirectToNewSneakerPage(Model model, @RequestParam Long modelId) {
        SneakerRequestDto sneakerRequestDto = new SneakerRequestDto();
        sneakerRequestDto.setModelId(modelId);
        model.addAttribute("sneaker", sneakerRequestDto);
        model.addAttribute("genders", Gender.values());
        return "pages/admin/sneakers/sneaker_new";
    }

    @PostMapping("/new")
    public String createNewSneaker(@ModelAttribute("sneaker") SneakerRequestDto dto) {
        sneakerFacade.create(dto);
        return "redirect:/admin/sneakers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        sneakerFacade.delete(id);
        return "redirect:/admin/sneakers";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        SneakerResponseDto resDto = sneakerFacade.findById(id);
        SneakerRequestDto dto = new SneakerRequestDto();
        dto.setId(id);
        model.addAttribute("sneakerVersion", resDto.getVersionOfModel());
        model.addAttribute("sneaker", dto);
        model.addAttribute("genders", Gender.values());
        return "pages/admin/sneakers/sneaker_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("sneaker") SneakerRequestDto sneaker) {
        sneakerFacade.update(sneaker);
        return "redirect:/admin/sneakers";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        SneakerResponseDto dto = sneakerFacade.findById(id);
        model.addAttribute("sneaker", dto);
        return "pages/admin/sneakers/sneaker_details";
    }
}
