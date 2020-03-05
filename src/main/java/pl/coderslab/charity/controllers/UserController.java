package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.services.EmailService;
import pl.coderslab.charity.services.EmailServiceImpl;
import pl.coderslab.charity.services.registration.OnRegistrationCompleteEvent;
import pl.coderslab.charity.services.user.UserService;

import javax.validation.Valid;
import java.util.Calendar;

@Controller()
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(UserService userService, EmailServiceImpl emailService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.emailService = emailService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Endpoint to create 1st Admin account
     * @return
     */
    /*
    @GetMapping("/create_admin")
    @ResponseBody
    public String createAdmin(){
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmail("admin@admin.pl");
        user.setPassword("admin");
        user.setRole(User.Role.ROLE_ADMIN);
        user.setActive(true);
        userService.saveUser(user);

        return "Admin created";
    }
     */

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") @Valid UserDto userDto,
                               BindingResult result,
                               WebRequest request) {
        User user = userService.findByEmail(userDto.getEmail());

        if (user != null) {
            FieldError error = new FieldError("userDto", "email", "Ten email już jest zarejestrowany");
            result.addError(error);
            return "registration";
        }

        if (!userDto.samePassword()) {
            FieldError error = new FieldError("userDto", "password", "Hasła są nie takie same");
            result.addError(error);
            return "registration";
        }

        user = userService.registerNewUser(userDto);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

        return "registrationConfirmation";
    }

    @GetMapping("/register/confirm/{token}")
    public String confirmRegistration(@PathVariable String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            //TODO Error handling, token not found
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            //TODO Error handling, expired token
        }

        user.setActive(true);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
