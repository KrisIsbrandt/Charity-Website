package pl.coderslab.charity.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.services.LoggedUser;

@Controller
public class AppController {

    @ModelAttribute("loggedUserName")
    private String showLoggedUserName(@AuthenticationPrincipal LoggedUser loggedUser){
        return loggedUser.getUser().getFirstName();
    }

    @GetMapping("/app")
    public String HomePage(){
        return "app/homepage";

    }

    //profil
    @GetMapping("/app/profil")
    public String getProfil(@AuthenticationPrincipal LoggedUser loggedUser,
                            Model model){
        model.addAttribute("user", loggedUser.getUser());
        return "/app/profil";
    }

    @PostMapping("/app/profil")
    public String updateProfil(){

        return "/app/profil";
    }

    //my_donations
    @GetMapping("/app/my_donations")
    public String getDonations(){

        return "/app/myDonations";
    }
}
