package ua.com.alevel.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.view.dto.user.UserRequestDto;
import ua.com.alevel.view.dto.user.UserResponseDto;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    public String all(Model model) {
        List<UserResponseDto> users = userFacade.findAll();
        model.addAttribute("users", users);
        return "pages/user/user_all";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        UserResponseDto user = userFacade.findById(id);
        model.addAttribute("user", user);
        return "pages/user/user_details";
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "pages/user/user_new";
    }

    @PostMapping("/new")
    public String createNewUser(@ModelAttribute("user") UserRequestDto dto) {
        userFacade.create(dto);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userFacade.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String redirectToEditPage(@RequestParam Long id, Model model) {
        UserResponseDto resDto = userFacade.findById(id);
        UserRequestDto dto = new UserRequestDto();
        dto.setId(id);
        model.addAttribute("userName", resDto.getFirstName() + " " + resDto.getLastName());
        model.addAttribute("user", dto);
        return "pages/user/user_edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") UserRequestDto dto) {
        userFacade.update(dto);
        return "redirect:/users";
    }
}
