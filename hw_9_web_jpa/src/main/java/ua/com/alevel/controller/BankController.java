package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.dto.LinkRequestDto;
import ua.com.alevel.dto.PageData;
import ua.com.alevel.dto.bank.BankRequestDto;
import ua.com.alevel.dto.bank.BankResponseDto;
import ua.com.alevel.dto.client.ClientResponseDto;
import ua.com.alevel.facade.BankFacade;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.type.BankType;

import java.util.List;

@Validated
@Controller
@RequestMapping("/banks")
public class BankController extends BaseController {

    private final BankFacade bankFacade;
    private final ClientFacade clientFacade;

    private final HeaderName[] columnNames = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("year", "yearOfFoundation", "year_of_foundation"),
            new HeaderName("client count", "clientCount", "clientCount"),
            new HeaderName("bank type", "bankType", "bank_type"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    public BankController(BankFacade bankFacade, ClientFacade clientFacade) {
        this.bankFacade = bankFacade;
        this.clientFacade = clientFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request, @RequestParam(required = false) Long clientId) {
        PageData<BankResponseDto> response;
        if(clientId != null) {
            response = bankFacade.findAllByClientId(request, clientId);
            model.addAttribute("createNew", "/banks/link?clientId=" + clientId);
        } else {
            response = bankFacade.findAll(request);
            model.addAttribute("createNew", "/banks/new");
        }
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/banks/all");
        model.addAttribute("cardHeader", "All Banks");
        return "pages/banks/banks_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "banks");
    }

    @GetMapping("/new")
    public String redirectToNewBankPage(Model model) {
        model.addAttribute("bank", new BankRequestDto());
        model.addAttribute("types", BankType.values());
        return "pages/banks/banks_new";
    }

    @PostMapping("/new")
    public String createNewBank(@ModelAttribute("bank") BankRequestDto dto) {
        bankFacade.create(dto);
        return "redirect:/banks";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bankFacade.delete(id);
        return "redirect:/banks";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        BankResponseDto dto = bankFacade.findById(id);
        model.addAttribute("bank", dto);
        return "pages/banks/banks_details";
    }

    @GetMapping("/link")
    public String redirectToLinkPage(Model model, @RequestParam Long clientId, WebRequest request) {
        ClientResponseDto dto = clientFacade.findById(clientId);
        List<BankResponseDto> banks = bankFacade.findAll(request).getItems();
        LinkRequestDto linkRequestDto = new LinkRequestDto();
        linkRequestDto.setClientId(clientId);
        model.addAttribute("client", dto);
        model.addAttribute("banks", banks);
        model.addAttribute("link", linkRequestDto);
        model.addAttribute("buttonText", "Link");
        model.addAttribute("pageTitle", "Link bank to client");
        model.addAttribute("action", "/banks/link");
        return "pages/relation/link";
    }

    @PostMapping("/link")
    public String link(@ModelAttribute("link") LinkRequestDto dto) {
        bankFacade.link(dto);
        return "redirect:/banks";
    }

    @GetMapping("/unlink")
    public String redirectToUnlinkPage(Model model, @RequestParam Long clientId, WebRequest request) {
        ClientResponseDto dto = clientFacade.findById(clientId);
        List<BankResponseDto> banks = bankFacade.findAllByClientId(request, clientId).getItems();
        LinkRequestDto linkRequestDto = new LinkRequestDto();
        linkRequestDto.setClientId(clientId);
        model.addAttribute("client", dto);
        model.addAttribute("banks", banks);
        model.addAttribute("link", linkRequestDto);
        model.addAttribute("buttonText", "Unlink");
        model.addAttribute("pageTitle", "Unlink client from bank");
        model.addAttribute("action", "/banks/unlink");
        return "pages/relation/link";
    }

    @PostMapping("/unlink")
    public String unlink(@ModelAttribute("link") LinkRequestDto dto) {
        bankFacade.unlink(dto);
        return "redirect:/banks";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        BankResponseDto resDto = bankFacade.findById(id);
        BankRequestDto dto = new BankRequestDto();
        dto.setId(id);
        model.addAttribute("bankName", resDto.getName());
        model.addAttribute("bank", dto);
        model.addAttribute("types", BankType.values());
        return "pages/banks/banks_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("bank") BankRequestDto bank) {
        bankFacade.update(bank);
        return "redirect:/banks";
    }
}