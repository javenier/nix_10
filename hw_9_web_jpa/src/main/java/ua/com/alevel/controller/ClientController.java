package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.dto.PageData;
import ua.com.alevel.dto.client.ClientRequestDto;
import ua.com.alevel.dto.client.ClientResponseDto;
import ua.com.alevel.facade.ClientFacade;


@Controller
@RequestMapping("/clients")
public class ClientController extends BaseController {

    private final ClientFacade clientFacade;

    private final HeaderName[] columnNames = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("first name", "firstName", "first_name"),
            new HeaderName("last name", "lastName", "last_name"),
            new HeaderName("bank count", "bankCount", "bankCount"),
            new HeaderName("age", "age", "age"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    public ClientController(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request, @RequestParam(required = false) Long bankId) {
        PageData<ClientResponseDto> response;
        boolean buttonVisible;
        if(bankId != null) {
            response = clientFacade.findAllByBankId(request, bankId);
            buttonVisible = true;
        } else {
            response = clientFacade.findAll(request);
            buttonVisible = false;
        }
        initDataTable(response, columnNames, model);
        model.addAttribute("buttonVisible", buttonVisible);
        model.addAttribute("createUrl", "/clients/all");
        model.addAttribute("createNew", "/clients/new?bankId=" + bankId);
        model.addAttribute("cardHeader", "All Clients");
        return "pages/clients/clients_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "clients");
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id) {
        ClientResponseDto clientResponseDto = clientFacade.findById(id);
        model.addAttribute("client", clientResponseDto);
        return "pages/clients/clients_details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clientFacade.delete(id);
        return "redirect:/clients";
    }

    @GetMapping("/new")
    public String redirectToNewClientPage(Model model, @RequestParam Long bankId) {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setBankId(bankId);
        model.addAttribute("client", clientRequestDto);
        return "pages/clients/clients_new";
    }

    @PostMapping("/new")
    public String createNewClient(@ModelAttribute("client") ClientRequestDto dto) {
        clientFacade.create(dto);
        return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        ClientResponseDto clientResponseDto = clientFacade.findById(id);
        ClientRequestDto dto = new ClientRequestDto();
        dto.setId(id);
        model.addAttribute("clientName", clientResponseDto.getFirstName() + " " + clientResponseDto.getLastName());
        model.addAttribute("client", dto);
        return "pages/clients/clients_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("client") ClientRequestDto client) {
        clientFacade.update(client);
        return "redirect:/clients";
    }
}
