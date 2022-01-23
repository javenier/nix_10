package ua.com.alevel.view.controller.client;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.facade.OrderFacade;
import ua.com.alevel.facade.SizeFacade;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.cart.CartItemResponseDto;
import ua.com.alevel.view.dto.user.ClientRequestDto;
import ua.com.alevel.view.dto.user.ClientResponseDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping
public class ClientController {

    private final ClientFacade clientFacade;
    private final OrderFacade orderFacade;
    private final SizeFacade sizeFacade;

    public ClientController(ClientFacade clientFacade, OrderFacade orderFacade,
                            SizeFacade sizeFacade) {
        this.clientFacade = clientFacade;
        this.orderFacade = orderFacade;
        this.sizeFacade = sizeFacade;
    }

    @GetMapping
    public String mainPage() {
        return "pages/main";
    }

    @GetMapping("/profile")
    public String profile(Model model, @RequestParam(required = false) boolean update) {
        User loggedInUser = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        ClientResponseDto client = clientFacade.findByEmail(loggedInUser.getUsername());
        if((client.getFirstName() == null && client.getLastName() == null &&
                client.getAge() == null && client.getGender() == null) || update) {
            ClientRequestDto requestDto = new ClientRequestDto();
            requestDto.setId(client.getId());
            model.addAttribute("client", client);
            model.addAttribute("requestDto", requestDto);
            model.addAttribute("genders", Gender.values());
            return "pages/client/profile_update";
        }
        model.addAttribute("client", client);
        return "pages/client/profile";
    }

    @PostMapping("/profile")
    public String fillProfile(@ModelAttribute("requestDto") ClientRequestDto clientRequestDto) {
        clientFacade.update(clientRequestDto);
        return "redirect:/catalogue";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "pages/contacts";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        User loggedInUser = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        ClientResponseDto client = clientFacade.findByEmail(loggedInUser.getUsername());
        Long orderId = clientFacade.findUnfinishedOrderId(client.getId());
        Order order;
        if(orderId != null) {
            order = orderFacade.findEntityById(orderId);
        } else {
            model.addAttribute("cartItems", Collections.emptyList());
            model.addAttribute("countOfItems", 0);
            model.addAttribute("totalPrice", 0);
            return "pages/client/cart";
        }
        List<CartItemResponseDto> cartItems = generateListOfCartItems(order);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("countOfItems", order.getSneakers().size());
        model.addAttribute("totalPrice", MoneyConverterUtil.pennyToString(order.getTotalPrice()));
        return "pages/client/cart";
    }

    List<CartItemResponseDto> generateListOfCartItems(Order order) {
        List<CartItemResponseDto> cartItems = new ArrayList<>();
        for(Sneaker sneaker : order.getSneakers()) {
            CartItemResponseDto cartItem = new CartItemResponseDto();
            cartItem.setImageUrl(sneaker.getImageUrl());
            cartItem.setBrand(sneaker.getModel().getBrand().getName());
            cartItem.setModelAndVersion(sneaker.getModel().getName() + " " +
                    sneaker.getVersionOfModel());
            cartItem.setPrice(MoneyConverterUtil.pennyToString(sneaker.getPrice()));
            cartItem.setSize(sizeFacade.findById(order.
                    getSneakerSizeForCurrentOrder().
                    get(sneaker.getId())).getSize());
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}