package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.coderslab.charity.services.EmailService;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;
    
}
