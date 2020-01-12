package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.repositories.CategoryRepository;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;

import java.util.List;

@Controller
public class DonationController {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public DonationController(InstitutionRepository institutionRepository,
                          DonationRepository donationRepository,
                          CategoryRepository categoryRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
    }

    @ModelAttribute("categories")
    private List<Category> categoryList() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("institutions")
    private List<Institution> institutionList() {
        return institutionRepository.findAll();
    }

    @GetMapping("/donation")
    public String donationForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "donationForm";
    }

    @PostMapping("/donation")
    @ResponseBody
    public String donationFormReceived(@ModelAttribute Donation donation) {
        System.out.println(donation);
        //TODO odbior, biddowanie i walidacja formulrza
        return "Udalo sie";
    }
}
