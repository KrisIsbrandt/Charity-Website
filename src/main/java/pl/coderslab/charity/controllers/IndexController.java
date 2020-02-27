package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;

import java.util.List;


@Controller
public class IndexController {

    private InstitutionRepository institutionRepository;
    private DonationRepository donationRepository;

    @Autowired
    public IndexController(InstitutionRepository institutionRepository, DonationRepository donationRepository) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
    }

    @GetMapping({"", "/"})
    public String homePage(Model model){
        List<Institution> institutions = institutionRepository.findAll();
        model.addAttribute("institutions", institutions);

        Long numberOfDonations = donationRepository.getSumOfDonationQuantity();
        model.addAttribute("numberOfDonations", numberOfDonations);

        Long numberOfSupportedInstitutions = donationRepository.getCountOfInstitutionWithDonations();
        model.addAttribute("numberOfSupportedInstitutions", numberOfSupportedInstitutions);
        return "index";
    }

    @GetMapping("/idea")
    public String ideaPage(){
        //TODO idea page
        return "redirect:/";
    }

    @GetMapping("/about_us")
    public String aboutUsPage(){
        //TODO about us info
        return "redirect:/";
    }

    @GetMapping("/institution")
    public String institutionPage(){
        //TODO list of supported institutions
        return "redirect:/";
    }

    @GetMapping("/contact")
    public String contactPage(){
        //TODO contact page
        return "redirect:/";
    }
}
