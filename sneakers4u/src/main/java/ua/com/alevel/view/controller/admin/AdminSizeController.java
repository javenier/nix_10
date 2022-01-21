package ua.com.alevel.view.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.ModelFacade;
import ua.com.alevel.facade.SizeFacade;
import ua.com.alevel.facade.SneakerFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.link.SneakerSizeLinkRequestDto;
import ua.com.alevel.view.dto.model.ModelResponseDto;
import ua.com.alevel.view.dto.size.SizeRequestDto;
import ua.com.alevel.view.dto.size.SizeResponseDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

import java.util.List;

@Controller
@RequestMapping("/admin/sizes")
public class AdminSizeController extends BaseController {

    private final SizeFacade sizeFacade;
    private final SneakerFacade sneakerFacade;

    public AdminSizeController(SizeFacade sizeFacade, ModelFacade modelFacade, SneakerFacade sneakerFacade) {
        this.sizeFacade = sizeFacade;
        this.sneakerFacade = sneakerFacade;
    }

    private final HeaderName[] columnNamesForFindAll = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("size", "size", "size"),
            new HeaderName("delete", null, null)
    };

    @GetMapping
    public String allSizes(Model model, WebRequest request, @RequestParam(required = false) Long sneakerId) {
        PageData<SizeResponseDto> pageData;
        if(sneakerId != null) {
            pageData = sizeFacade.findAllBySneakerId(request, sneakerId);
        } else {
            pageData = sizeFacade.findAll(request);
        }
        initDataTable(pageData, columnNamesForFindAll, model);
        model.addAttribute("pageData", pageData);
        model.addAttribute("createUrl", "sizes/all");
        model.addAttribute("cardHeader", "All sizes");
        model.addAttribute("createNew", "sizes/new");
        model.addAttribute("buttonVisible", true);
        return "pages/admin/sizes/sizes_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/sizes");
    }

    @GetMapping("/new")
    public String redirectToNewSizePage(Model model) {
        model.addAttribute("size", new SizeRequestDto());
        return "pages/admin/sizes/size_new";
    }

    @PostMapping("/new")
    public String createNewSize(@ModelAttribute("size") SizeRequestDto dto) {
        sizeFacade.create(dto);
        return "redirect:/admin/sizes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        sizeFacade.delete(id);
        return "redirect:/admin/sizes";
    }

    @GetMapping("/link")
    public String redirectToLinkPage(Model model, @RequestParam Long sneakerId, WebRequest request) {
        SneakerResponseDto dto = sneakerFacade.findById(sneakerId);
        List<SizeResponseDto> sizes = sizeFacade.findAll(request).getItems();
        SneakerSizeLinkRequestDto linkRequestDto = new SneakerSizeLinkRequestDto();
        linkRequestDto.setSneakerId(sneakerId);
        model.addAttribute("sneaker", dto);
        model.addAttribute("sizes", sizes);
        model.addAttribute("link", linkRequestDto);
        model.addAttribute("buttonText", "Link");
        model.addAttribute("pageTitle", "Link size to sneaker");
        model.addAttribute("action", "/admin/sizes/link");
        return "pages/admin/relation/sneaker_size";
    }

    @PostMapping("/link")
    public String link(@ModelAttribute("link") SneakerSizeLinkRequestDto dto) {
        sizeFacade.link(dto);
        return "redirect:/admin/sneakers";
    }

    @GetMapping("/unlink")
    public String redirectToUnlinkPage(Model model, @RequestParam Long sneakerId, WebRequest request) {
        SneakerResponseDto dto = sneakerFacade.findById(sneakerId);
        List<SizeResponseDto> sizes = sizeFacade.findAllBySneakerId(request, sneakerId).getItems();
        SneakerSizeLinkRequestDto linkRequestDto = new SneakerSizeLinkRequestDto();
        linkRequestDto.setSneakerId(sneakerId);
        model.addAttribute("sneaker", dto);
        model.addAttribute("sizes", sizes);
        model.addAttribute("link", linkRequestDto);
        model.addAttribute("buttonText", "Unlink");
        model.addAttribute("pageTitle", "Unlink size from sneaker");
        model.addAttribute("action", "/admin/sizes/unlink");
        return "pages/admin/relation/sneaker_size";
    }

    @PostMapping("/unlink")
    public String unlink(@ModelAttribute("link") SneakerSizeLinkRequestDto dto) {
        sizeFacade.unlink(dto);
        return "redirect:/admin/sneakers";
    }
}
