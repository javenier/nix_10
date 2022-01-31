package ua.com.alevel.view.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.OrderFacade;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.order.OrderRequestDto;
import ua.com.alevel.view.dto.order.OrderResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController extends BaseController {

    private final OrderFacade orderFacade;

    private final HeaderName[] columnNamesForFindAll = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("id", "id", "id"),
            new HeaderName("total price", "totalPrice", "total_price"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    public AdminOrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping
    public String allOrders(Model model, WebRequest request, @RequestParam(required = false) Long clientId) {
        PageData<OrderResponseDto> pageData;
        if (clientId != null) {
            pageData = orderFacade.findAllByClientId(request, clientId);
        } else {
            pageData = orderFacade.findAll(request);
        }
        initDataTable(pageData, columnNamesForFindAll, model);
        model.addAttribute("pageData", pageData);
        model.addAttribute("createUrl", "orders/all");
        model.addAttribute("cardHeader", "All orders");
        model.addAttribute("buttonVisible", false);
        return "pages/admin/orders/orders_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/orders");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderFacade.delete(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        OrderResponseDto resDto = orderFacade.findById(id);
        OrderRequestDto dto = new OrderRequestDto();
        dto.setId(id);
        model.addAttribute("order", dto);
        return "pages/admin/orders/order_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("order") OrderRequestDto order) {
        orderFacade.update(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        OrderResponseDto dto = orderFacade.findById(id);
        model.addAttribute("order", dto);
        return "pages/admin/orders/order_details";
    }
}
