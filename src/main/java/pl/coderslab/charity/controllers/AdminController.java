package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.repositories.CategoryRepository;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;
import pl.coderslab.charity.repositories.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private InstitutionRepository institutionRepository;
    private CategoryRepository categoryRepository;
    private DonationRepository donationRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminController(InstitutionRepository institutionRepository,
                           CategoryRepository categoryRepository,
                           DonationRepository donationRepository,
                           UserRepository userRepository) {
        this.institutionRepository = institutionRepository;
        this.categoryRepository = categoryRepository;
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String showAdminPanel(){
        return "admin/adminPanel";
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
                break;

            case "user":
                User user = new User();
                if (id != null) {
                    user = userRepository.getOne(id);
                }
                model.addAttribute("user",user);
                break;
        }
        return "admin/adminForm";
    }

    /**
     * Institutions management*/
    @GetMapping("/institution")
    public String showInstitution(Model model){
        List<Institution> institutions = institutionRepository.findAll();
        model.addAttribute("institutions", institutions);
        return "admin/adminInstitution";
    }

    @PostMapping("/institution")
    public String addInstitution(Institution institution) {
        institution.toString();
        institutionRepository.save(institution);
        return "redirect:/admin/institution";
    }

    @GetMapping("/institution/delete/{id}")
    public String deleteInstition(@PathVariable Long id) {
        institutionRepository.deleteById(id);
        return "redirect:/admin/institution";
    }
}
