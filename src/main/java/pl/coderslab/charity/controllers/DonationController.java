package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.entities.Donation;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.repositories.CategoryRepository;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;
import pl.coderslab.charity.services.LoggedUser;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Controller()
public class DonationController {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;
    private CategoryRepository categoryRepository;
    private Validator validator;

    @Autowired
    public DonationController(InstitutionRepository institutionRepository,
                              DonationRepository donationRepository,
                              CategoryRepository categoryRepository,
                              Validator validator) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
        this.categoryRepository = categoryRepository;
        this.validator = validator;
    }

    @ModelAttribute("loggedUserName")
    private String showLoggedUserName(@AuthenticationPrincipal LoggedUser loggedUser){
        return loggedUser.getUser().getFirstName();
    }

    @ModelAttribute("categories")
    private List<Category> categoryList() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("institutions")
    private List<Institution> institutionList() {
        return institutionRepository.findAll();
    }

    @GetMapping("app/donation")
    public String donationForm(Model model) {
        model.addAttribute("donation", new Donation());
        return "app/donationForm";
    }

    @PostMapping("app/donation")
    public String donationFormReceived(@Valid Donation donation, BindingResult result) {
        if(result.hasErrors()){
            return "app/donationForm";
        }
        donationRepository.save(donation);

        return "app/donationFormConfirmation";
    }
}
