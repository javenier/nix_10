package ua.com.alevel.view.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.alevel.config.security.SecurityService;
import ua.com.alevel.facade.AuthValidatorFacade;
import ua.com.alevel.facade.RegistrationFacade;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.service.ClientService;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.auth.AuthDto;

@Controller
public class AuthController extends BaseController {

    private final RegistrationFacade registrationFacade;
    private final AuthValidatorFacade authValidatorFacade;
    private final SecurityService securityService;
    private final ClientService clientService;

    public AuthController(
            RegistrationFacade registrationFacade,
            AuthValidatorFacade authValidatorFacade,
            SecurityService securityService, ClientService clientService) {
        this.registrationFacade = registrationFacade;
        this.authValidatorFacade = authValidatorFacade;
        this.securityService = securityService;
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        showMessage(model, false);
        boolean authenticated = securityService.isAuthenticated();
        if (authenticated) {
            if (SecurityUtil.hasRole(RoleType.ROLE_ADMIN.name())) {
                return "redirect:/admin/sneakers";
            }
            if (SecurityUtil.hasRole(RoleType.ROLE_CLIENT.name())) {
                return "redirect:/";
            }
        }
        if (error != null) {
            showError(model, "Your email and password is invalid.");
        }
        if (logout != null) {
            showInfo(model, "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return redirectProcess(model);
        }
        model.addAttribute("authForm", new AuthDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("authForm") AuthDto authForm, BindingResult bindingResult, Model model) {
        showMessage(model, false);
        authValidatorFacade.validate(authForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationFacade.registration(authForm);
        securityService.autoLogin(authForm.getEmail(), authForm.getConfirmPassword());
        return redirectProcess(model);
    }

    @GetMapping("/activate/{code}")
    public String activation(Model model, @PathVariable String code) {
        boolean isActivated = clientService.isActivatedByEmail(code);
        if(isActivated) {
            model.addAttribute("message", "You have successfully verified your email.");
        } else {
            model.addAttribute("message", "Code not found");
        }
        return "activation";

    }

    private String redirectProcess(Model model) {
        showMessage(model, false);
        if (SecurityUtil.hasRole(RoleType.ROLE_ADMIN.name())) {
            return "redirect:/admin";
        }
        if (SecurityUtil.hasRole(RoleType.ROLE_CLIENT.name())) {
            return "redirect:/";
        }
        return "redirect:/login";
    }
}
