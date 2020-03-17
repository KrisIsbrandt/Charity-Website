package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.repositories.CategoryRepository;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;
import pl.coderslab.charity.repositories.UserRepository;
import pl.coderslab.charity.services.LoggedUser;
import pl.coderslab.charity.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InstitutionRepository institutionRepository;
    private final CategoryRepository categoryRepository;
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AdminController(InstitutionRepository institutionRepository,
                           CategoryRepository categoryRepository,
                           DonationRepository donationRepository,
                           UserRepository userRepository,
                           UserService userService) {
        this.institutionRepository = institutionRepository;
        this.categoryRepository = categoryRepository;
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("")
    public String showAdminPanel(){
        return "admin/adminUser";
    }

    @GetMapping("/form")
    public String form(@RequestParam String type,
                       @RequestParam (required = false) Long id,
                       Model model) {
        model.addAttribute("type", type);

        switch (type) {
            case "institution":
                Institution institution = new Institution();
                if (id != null) {
                    institution = institutionRepository.getOne(id);
                }
                model.addAttribute("institution", institution);
                break;

            case "category":
                Category category = new Category();
                if (id != null) {
                    category = categoryRepository.getOne(id);
                }
                model.addAttribute("category", category);
                break;

            case "donation":
                Donation donation = new Donation();
                if (id != null) {
                    donation = donationRepository.getOne(id);
                }
                model.containsAttribute("donation");
                model.addAttribute("donation", donation);

                List<Institution> institutions = institutionRepository.findAll();
                model.addAttribute("institutions", institutions);

                List<Category> categories = categoryRepository.findAll();
                model.addAttribute("categories", categories);
                break;

            case "user":
                User user = new User();
                if (id != null) {
                    user = userRepository.getOne(id);
                }
                model.addAttribute("user",user);

                List<User.Role> roles = new ArrayList<>();
                roles.add(User.Role.ROLE_ADMIN);
                roles.add(User.Role.ROLE_USER);
                model.addAttribute("roles", roles);
                break;
        }
        return "admin/adminForm";
    }

    /**
     * Institutions management
     */
    @GetMapping("/institution")
    public String showInstitution(Model model){
        List<Institution> institutions = institutionRepository.findAll();
        model.addAttribute("institutions", institutions);
        return "admin/adminInstitution";
    }

    @PostMapping("/institution")
    public String addInstitution(@Valid Institution institution, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("type", "institution");
            return "admin/adminForm";
        }
        institutionRepository.save(institution);
        return "redirect:/admin/institution";
    }

    @GetMapping("/institution/delete/{id}")
    public String deleteInstition(@PathVariable Long id) {
        institutionRepository.deleteById(id);
        return "redirect:/admin/institution";
    }

    /**
     * Category management
     */
    @GetMapping("/category")
    public String showCategories(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/adminCategory";
    }

    @PostMapping("/category")
    public String addCategory(@Valid Category category, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("type", "category");
            return "admin/adminForm";
        }
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }


    /**
     * User management
     */
    @GetMapping("/user")
    public String showUsers(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/adminUser";
    }

    @PostMapping("/user")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("type", "user");
            return "admin/adminForm";
        }
        userService.saveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, @AuthenticationPrincipal LoggedUser loggedUser) {
        //You cannot delete yourself
        if (!loggedUser.getUser().getId().equals(id)) {
            userRepository.deleteById(id);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/user/state/{id}")
    public String changeUserState(@PathVariable Long id, @AuthenticationPrincipal LoggedUser loggedUser) {
        //You cannot change your own state
        if (!loggedUser.getUser().getId().equals(id)) {

            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                user.setActive(!user.isActive());
                user = userService.hashPassword(user);
                userService.saveUser(user);
            }
        }
        return "redirect:/admin/user";
    }


    /**
     * Donation management
     */
    @GetMapping("/donation")
    public String showDonations(Model model){
        List<Donation> donations = donationRepository.findAll();
        model.addAttribute("donations", donations);
        return "admin/adminDonation";
    }

    @PostMapping("/donation")
    public String addDonation(@Valid Donation donation, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("type", "donation");
            List<Institution> institutions = institutionRepository.findAll();
            model.addAttribute("institutions", institutions);

            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "admin/adminForm";
        }

        donationRepository.save(donation);
        return "redirect:/admin/donation";
    }

    @GetMapping("/donation/delete/{id}")
    public String deleteDonation(@PathVariable Long id) {
        donationRepository.deleteById(id);
        return "redirect:/admin/donation";
    }

    @GetMapping("/donation/confirm/pickup/{id}")
    public String confirmDonationPickUp(@PathVariable Long id) {
        Donation donation = donationRepository.getOne(id);
        donation.setPickedUp(!donation.isPickedUp());
        donationRepository.save(donation);
        return "redirect:/admin/donation";
    }

    @GetMapping("/donation/confirm/delivery/{id}")
    public String confirmDonationDelivery(@PathVariable Long id) {
        Donation donation = donationRepository.getOne(id);
        donation.setDeliveredToInstitution(!donation.isDeliveredToInstitution());
        donationRepository.save(donation);
        return "redirect:/admin/donation";
    }
}
