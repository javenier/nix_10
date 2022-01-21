package ua.com.alevel.view.controller.client;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.ClientFacade;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.view.dto.user.ClientRequestDto;
import ua.com.alevel.view.dto.user.ClientResponseDto;


@Controller
@RequestMapping
public class ClientController {

    private final ClientFacade clientFacade;

    public ClientController(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
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
}