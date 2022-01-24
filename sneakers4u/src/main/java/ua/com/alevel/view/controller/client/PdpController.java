package ua.com.alevel.view.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.config.security.SecurityService;
import ua.com.alevel.facade.SizeFacade;
import ua.com.alevel.facade.SneakerFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.cart.CartItemRequestDto;
import ua.com.alevel.view.dto.size.SizeResponseDto;
import ua.com.alevel.view.dto.sneaker.SneakerResponseDto;

import java.util.List;

@Controller
@RequestMapping("/catalogue/details")
public class PdpController extends BaseController {

    private final SneakerFacade sneakerFacade;
    private final SizeFacade sizeFacade;
    private final SecurityService securityService;

    public PdpController(SneakerFacade sneakerFacade, SizeFacade sizeFacade, SecurityService securityService) {
        this.sneakerFacade = sneakerFacade;
        this.sizeFacade = sizeFacade;
        this.securityService = securityService;
    }

    @GetMapping("/{id}")
    public String sneakerDetails(Model model, WebRequest request, @PathVariable Long id) {
        SneakerResponseDto sneaker = sneakerFacade.findById(id);
        List<SizeResponseDto> sizes = sizeFacade.findAllBySneakerId(request, id).getItems();
        CartItemRequestDto cartItemRequestDto = new CartItemRequestDto();
        cartItemRequestDto.setSneakerId(sneaker.getId());
        model.addAttribute("sneaker", sneaker);
        model.addAttribute("sizes", sizes);
        model.addAttribute("cartItem", cartItemRequestDto);
        if(sneaker.getQuantity() > 0) {
            model.addAttribute("inStock", true);
        } else {
            model.addAttribute("inStock", false);
        }
        return "pages/pdp/pdp";
    }

    @PostMapping("/{id}")
    public String addToCart(@ModelAttribute("cartItem") CartItemRequestDto cartItemRequestDto) {
        if(!securityService.isAuthenticated()) {
            return "redirect:/login";
        }
        sneakerFacade.addToCart(cartItemRequestDto);
        return "redirect:/catalogue";
    }
}
