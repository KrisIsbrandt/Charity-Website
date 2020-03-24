package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.repositories.DonationRepository;
import pl.coderslab.charity.repositories.InstitutionRepository;
import pl.coderslab.charity.services.EmailService;
import pl.coderslab.charity.services.LoggedUser;

import java.nio.charset.Charset;
import java.util.List;


@Controller
public class IndexController {

    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    private final EmailService emailService;

    @Value("${spring.mail.username}")
    private String applicationEmailAddress;

    @Autowired
    public IndexController(InstitutionRepository institutionRepository, DonationRepository donationRepository, EmailService emailService) {
        this.institutionRepository = institutionRepository;
        this.donationRepository = donationRepository;
        this.emailService = emailService;
    }

    @ModelAttribute("loggedUserName")
    private String showLoggedUserName(@AuthenticationPrincipal LoggedUser loggedUser){
        if (loggedUser == null) {
            return "";
        }
        return loggedUser.getUser().getFirstName();
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

    @PostMapping("/contactForm")
    public String contactForm(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name)
          .append("Email: ").append(email)
          .append("Message: ").append(message);

        emailService.sendEmail(applicationEmailAddress, "Contact Form Message", sb.toString());
        return "contactFormConfirmation";
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

    @GetMapping("/test")
    public String testGet() {
        return "test";
    }

    @PostMapping("/test")
    @ResponseBody
    public String testPost(@RequestParam ("text") String text) {
        return text;
    }

    @GetMapping("/charset")
    @ResponseBody
    public String charset() {
        return Charset.defaultCharset().displayName();
    }
}
