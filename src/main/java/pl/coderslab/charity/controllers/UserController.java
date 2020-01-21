package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.services.UserService;

import javax.validation.Valid;

@Controller()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-admin")
    @ResponseBody
    public String createAdmin() {
        User user = new User();
        user.setEmail("admin@admin.pl");
        user.setPassword("admin");
        userService.saveUser(user);
        return "admin created";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerPost(@Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }

        User user = userService.findByEmail(userDto.getEmail());

        if (user != null) {
           FieldError error = new FieldError("userDto", "email", "Ten email już jest zarejestrowany");
           result.addError(error);
           return "registration";
        }

        if (!userDto.samePassord()) {
            FieldError error = new FieldError("userDto", "password", "Hasła są nie takie same");
            result.addError(error);
            return "registration";
        }

        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(User.Role.ROLE_USER);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userService.saveUser(user);

        return "redirect:/login";
    }
}
