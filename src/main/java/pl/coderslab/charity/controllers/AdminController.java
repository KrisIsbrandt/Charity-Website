package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import pl.coderslab.charity.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private InstitutionRepository institutionRepository;
    private CategoryRepository categoryRepository;
    private DonationRepository donationRepository;
    private UserRepository userRepository;
    private UserService userService;

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
    public String addInstitution(Institution institution) {
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
    public String addCategory(Category category) {
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
    public String addUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
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
    public String addDonation(@Valid Donation donation, BindingResult result) {
        if (result.hasErrors()){

            return "redirect:/admin/form?type=donation";
        }

        donationRepository.save(donation);
        return "redirect:/admin/donation";
    }

    @GetMapping("/donation/delete/{id}")
    public String deleteDonation(@PathVariable Long id) {
        donationRepository.deleteById(id);
        return "redirect:/admin/donation";
    }
}
