package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entities.Institution;
import pl.coderslab.charity.repositories.InstitutionRepository;

import java.util.List;


@Controller
public class HomeController {

    private InstitutionRepository institutionRepository;

    @Autowired
    public HomeController(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @RequestMapping("/")
    public String homeAction(Model model){
        List<Institution> institutions = institutionRepository.findAll();
        model.addAttribute("institutions", institutions);
        return "index";
    }
}
