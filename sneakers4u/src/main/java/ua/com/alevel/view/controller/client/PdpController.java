package ua.com.alevel.view.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.SizeFacade;
import ua.com.alevel.facade.SneakerFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.size.SizeResponseDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;

import java.util.List;

@Controller
@RequestMapping("/catalogue/details")
public class PdpController extends BaseController {

    private final SneakerFacade sneakerFacade;
    private final SizeFacade sizeFacade;

    public PdpController(SneakerFacade sneakerFacade, SizeFacade sizeFacade) {
        this.sneakerFacade = sneakerFacade;
        this.sizeFacade = sizeFacade;
    }

    @GetMapping("/{id}")
    public String sneakerDetails(Model model, WebRequest request, @PathVariable Long id) {
        SneakerResponseDto sneaker = sneakerFacade.findById(id);
        List<SizeResponseDto> sizes = sizeFacade.findAllBySneakerId(request, id).getItems();
        model.addAttribute("sneaker", sneaker);
        model.addAttribute("sizes", sizes);
        return "pages/pdp/pdp";
    }
}
