package ua.com.alevel.view.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.user.ClientRequestDto;
import ua.com.alevel.view.dto.user.ClientResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

@Controller
@RequestMapping({"/admin", "/admin/clients"})
public class AdminController extends BaseController {

    private final ClientFacade clientFacade;

    private final HeaderName[] columnNamesForFindAll = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("email", "email", "email"),
            new HeaderName("enabled", null, null),
            new HeaderName("details", null, null),
            new HeaderName("enablement", null, null),
            new HeaderName("delete", null, null)
    };

    public AdminController(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @GetMapping
    public String allClients(Model model, WebRequest request) {
        PageData<ClientResponseDto> pageData = clientFacade.findAll(request);
        initDataTable(pageData, columnNamesForFindAll, model);
        model.addAttribute("pageData", pageData);
        model.addAttribute("createUrl", "clients/all");
        model.addAttribute("cardHeader", "All clients");
        model.addAttribute("createNew", "clients/new");
        model.addAttribute("buttonVisible", false);
        return "pages/admin/clients/clients_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/clients");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clientFacade.delete(id);
        return "redirect:/admin/clients";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        ClientResponseDto resDto = clientFacade.findById(id);
        ClientRequestDto dto = new ClientRequestDto();
        dto.setId(id);
        model.addAttribute("client", dto);
        model.addAttribute("clientEmail", resDto.getEmail());
        model.addAttribute("genders", Gender.values());
        return "pages/admin/clients/client_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("client") ClientRequestDto client) {
        clientFacade.update(client);
        return "redirect:/admin/clients";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        ClientResponseDto dto = clientFacade.findById(id);
        model.addAttribute("client", dto);
        return "pages/admin/clients/client_details";
    }

    @GetMapping("/disable/{id}")
    public String disableClient(@PathVariable Long id) {
        clientFacade.disableClient(id);
        return "redirect:/admin/clients";
    }

    @GetMapping("/enable/{id}")
    public String enableClient(@PathVariable Long id) {
        clientFacade.enableClient(id);
        return "redirect:/admin/clients";
    }
}
