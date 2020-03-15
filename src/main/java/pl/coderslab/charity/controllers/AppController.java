package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.services.LoggedUser;
import pl.coderslab.charity.services.UserService;

import javax.validation.Valid;

@Controller
public class AppController {

    private final UserService userService;

    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loggedUserName")
    private String showLoggedUserName(@AuthenticationPrincipal LoggedUser loggedUser){
        if (loggedUser == null) {
            return "";
        }
        return loggedUser.getUser().getFirstName();
    }

    @GetMapping("/app")
    public String HomePage(){
        return "app/homepage";

    }

    //profil
    @GetMapping("/app/profil")
    public String getProfil(@AuthenticationPrincipal LoggedUser loggedUser, Model model) {
        model.addAttribute("user", loggedUser.getUser());
        return "/app/profil";
    }

    @GetMapping("/app/profil/update")
    public String getProfilUpdateForm(@AuthenticationPrincipal LoggedUser loggedUser, Model model) {
        UserDto userDto = new UserDto();
        userDto.setEmail(loggedUser.getUser().getEmail());
        userDto.setFirstName(loggedUser.getUser().getFirstName());
        userDto.setLastName(loggedUser.getUser().getLastName());
        userDto.setPassword("NON_EMPTY_STRING");
        model.addAttribute("userDto", userDto);
        return "/app/profilForm";
    }

    @PostMapping("/app/profil/update")
    public String updateProfil(@AuthenticationPrincipal LoggedUser loggedUser,
                               @ModelAttribute("userDto") @Valid UserDto userDto,
                               BindingResult result){
        if (result.hasErrors()) {
            return "/app/profilForm";
        }
        User user = loggedUser.getUser();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userService.saveUser(user);
        return "redirect:/app/profil";
    }

    //my_donations
    @GetMapping("/app/my_donations")
    public String getDonations(){

        return "/app/myDonations";
    }
}
