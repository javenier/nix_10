package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.persistence.type.AccountType;
import ua.com.alevel.view.dto.account.AccountRequestDto;
import ua.com.alevel.view.dto.account.AccountResponseDto;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController extends BaseController {

    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @GetMapping
    public String all(@RequestParam(required = false) Long userId, Model model) {
        List<AccountResponseDto> accounts;
        if(userId != null) {
            accounts = accountFacade.findAllByUserId(userId);
        } else {
            accounts = accountFacade.findAll();
        }
        model.addAttribute("accounts", accounts);
        return "pages/account/account_all";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        AccountResponseDto account = accountFacade.findById(id);
        model.addAttribute("account", account);
        return "pages/account/account_details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        accountFacade.delete(id);
        return "redirect:/accounts";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        AccountResponseDto resDto = accountFacade.findById(id);
        AccountRequestDto dto = new AccountRequestDto();
        dto.setId(id);
        model.addAttribute("account", dto);
        model.addAttribute("types", AccountType.values());
        return "pages/account/account_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("account") AccountRequestDto dto) {
        accountFacade.update(dto);
        return "redirect:/accounts";
    }

    @GetMapping("/new")
    public String redirectToNewAccountPage(@RequestParam Long userId, Model model) {
        AccountRequestDto dto = new AccountRequestDto();
        dto.setUserId(userId);
        model.addAttribute("account", dto);
        model.addAttribute("types", AccountType.values());
        return "pages/account/account_new";
    }

    @PostMapping("/new")
    public String createNewAccount(@ModelAttribute("account") AccountRequestDto dto) {
        accountFacade.create(dto);
        return "redirect:/accounts";
    }
}
