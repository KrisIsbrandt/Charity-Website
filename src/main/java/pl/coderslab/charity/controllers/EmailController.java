package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;
import pl.coderslab.charity.services.EmailService;
import pl.coderslab.charity.services.UserService;

@Controller
public class EmailController {

    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, TemplateEngine templateEngine, UserService userService) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.userService = userService;
    }
}
