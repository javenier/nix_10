package ua.com.alevel.view.controller.client;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.facade.OrderFacade;
import ua.com.alevel.facade.SizeFacade;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.entity.user.Client;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.cart.CartItemResponseDto;
import ua.com.alevel.view.dto.order.OrderRequestDto;
import ua.com.alevel.view.dto.order.OrderResponseDto;
import ua.com.alevel.view.dto.user.ClientRequestDto;
import ua.com.alevel.view.dto.user.ClientResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

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
            model.addAttribute("buttonVisible", false);
            return "pages/client/cart";
        }
        List<CartItemResponseDto> cartItems = generateListOfCartItems(order);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("countOfItems", order.getSneakers().size());
        model.addAttribute("totalPrice", MoneyConverterUtil.pennyToString(order.getTotalPrice()));
        model.addAttribute("orderId", orderId);
        if(order.getSneakers().size() == 0)
            model.addAttribute("buttonVisible", false);
        else
            model.addAttribute("buttonVisible", true);
        return "pages/client/cart";
    }

    @GetMapping("/cart/delete")
    public String removeItemFromCart(@RequestParam Long orderId, @RequestParam Long sneakerId) {
        orderFacade.removeItemFromCart(sneakerId, orderId);
        return "redirect:/cart";
    }

    @GetMapping("/order/place")
    public String placeOrder(Model model, @RequestParam Long id) {
        OrderResponseDto order = orderFacade.findById(id);
        ClientResponseDto client = clientFacade.findByEmail(order.getClientEmail());
        if((client.getFirstName() == null && client.getLastName() == null &&
                client.getAge() == null && client.getGender() == null)) {
            return "redirect:/profile";
        }
        model.addAttribute("order", order);
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setId(order.getId());
        model.addAttribute("requestDto", requestDto);
        return "pages/client/place_order";
    }

    @PostMapping("/order/place")
    public String finishOrderPlacing(@ModelAttribute("requestDto") OrderRequestDto requestDto) {
        orderFacade.confirmOrder(requestDto);
        return "redirect:/";
    }

    @GetMapping("/profile/orders")
    public String allOrdersByClient(Model model, WebRequest request) {
        User loggedInUser = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        ClientResponseDto client = clientFacade.findByEmail(loggedInUser.getUsername());
        PageData<OrderResponseDto> orders = orderFacade.findAllByClientId(request, client.getId());
        model.addAttribute("orders", orders);
        return "pages/client/orders_all";
    }

    @GetMapping("/profile/orders/{id}")
    public String orderDetails(Model model, @PathVariable Long id) {
        Order order = orderFacade.findEntityById(id);
        model.addAttribute("order", order);
        model.addAttribute("totalPrice", MoneyConverterUtil.pennyToString(order.getTotalPrice()) + " $");
        return "pages/client/order_details";
    }

    List<CartItemResponseDto> generateListOfCartItems(Order order) {
        List<CartItemResponseDto> cartItems = new ArrayList<>();
        for(Sneaker sneaker : order.getSneakers()) {
            CartItemResponseDto cartItem = new CartItemResponseDto();
            cartItem.setId(sneaker.getId());
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