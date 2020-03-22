package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.services.LoggedUser;
import pl.coderslab.charity.services.UserService;

import javax.validation.Valid;
import java.util.List;

import static pl.coderslab.charity.entities.Donation.State.PICKED_UP;

@Controller
public class AppController {

    private final UserService userService;
    private final DonationRepository donationRepository;

    @Autowired
    public AppController(UserService userService, DonationRepository donationRepository) {
        this.userService = userService;
        this.donationRepository = donationRepository;
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
        return "app/profil";
    }

    @GetMapping("/app/profil/update")
    public String getProfilUpdateForm(@AuthenticationPrincipal LoggedUser loggedUser, Model model) {
        UserDto userDto = new UserDto();
        userDto.setEmail(loggedUser.getUser().getEmail());
        userDto.setFirstName(loggedUser.getUser().getFirstName());
        userDto.setLastName(loggedUser.getUser().getLastName());
        userDto.setPassword("NON_EMPTY_STRING");
        model.addAttribute("userDto", userDto);
        return "app/profilForm";
    }

    @PostMapping("/app/profil/update")
    public String updateProfil(@AuthenticationPrincipal LoggedUser loggedUser,
                               @ModelAttribute("userDto") @Valid UserDto userDto,
                               BindingResult result){
        if (result.hasErrors()) {
            return "app/profilForm";
        }
        User user = loggedUser.getUser();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userService.saveUser(user);
        return "redirect:/app/profil";
    }

    //myDonations
    @GetMapping("/app/donations")
    public String getDonations(@AuthenticationPrincipal LoggedUser loggedUser,
                               Model model){
        List<Donation> donations = donationRepository.findAllByUser(loggedUser.getUser());
        model.addAttribute("donations", donations);
        return "app/donations";
    }

    @GetMapping("app//donation/confirm/pickup/{id}")
    public String confirmDonationPickUp(@PathVariable Long id) {
        Donation donation = donationRepository.getOne(id);
        donation.setState(PICKED_UP);
        donationRepository.save(donation);
        return "redirect:/app/donations";
    }
}
