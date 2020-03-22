package pl.coderslab.charity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.coderslab.charity.dto.UserDto;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.events.OnPasswordResetEvent;
import pl.coderslab.charity.events.OnRegistrationCompleteEvent;
import pl.coderslab.charity.events.OnUserSelfActivation;
import pl.coderslab.charity.services.EmailService;
import pl.coderslab.charity.services.EmailServiceImpl;
import pl.coderslab.charity.services.LoggedUser;
import pl.coderslab.charity.services.UserService;

import javax.validation.Valid;
import java.util.Calendar;

import static pl.coderslab.charity.entities.VerificationToken.Type.ACCOUNT_ACTIVATION;
import static pl.coderslab.charity.entities.VerificationToken.Type.PASSWORD_RESET;

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

    @ModelAttribute("loggedUserName")
    private String showLoggedUserName(@AuthenticationPrincipal LoggedUser loggedUser){
        if (loggedUser == null) {
            return "";
        }
        return loggedUser.getUser().getFirstName();
    }

    /**
     * Endpoint to create 1st Admin account
     * @return
     */
    @GetMapping("/create_admin")
    @ResponseBody
    public String createFirstAdmin(){
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmail("admin@admin.pl");
        user.setPassword("admin");
        user.setRole(User.Role.ROLE_ADMIN);
        user.setActive(true);
        user = userService.hashPassword(user);
        userService.saveUser(user);
        return "Admin created " + user.toString();
    }

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
    public String registerPost(@ModelAttribute("userDto") @Valid UserDto userDto,
                               BindingResult result,
                               WebRequest request) {
        User user = userService.findByEmail(userDto.getEmail());

        //User exists
        if (user != null) {
            FieldError error = new FieldError("userDto", "email", "Ten email już jest zarejestrowany");
            result.addError(error);
            return "registration";
        }

        //Password mismatch
        if (!userDto.samePassword()) {
            FieldError error = new FieldError("userDto", "password", "Hasła są nie takie same");
            result.addError(error);
            return "registration";
        }

        user = userService.registerNewUser(userDto);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

        return "emailSendConfirmation";
    }

    @GetMapping("/confirm/{token}")
    public String confirmActionsWithToken(@PathVariable String token, Model model) {
        VerificationToken verificationToken = userService.getVerificationToken(token);

        //Not existing token
        if (verificationToken == null) {
            String message = "Taki kod weryfikacyjny nie istnieje";
            model.addAttribute("message", message);
            return "error";
        }

        //Expired token
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            userService.expireVerificationToken(verificationToken);
            String message = "Nieważny kod weryfikacyjny";
            model.addAttribute("message", message);
            return "error";
        }

        User user = verificationToken.getUser();
        //Account activation
        if (verificationToken.getType().equals(ACCOUNT_ACTIVATION)) {
            userService.expireVerificationToken(verificationToken);
            user.setActive(true);
            userService.saveUser(user);
            return "redirect:/login";
        }

        //Password reset
        if (verificationToken.getType().equals(PASSWORD_RESET)) {
            userService.expireVerificationToken(verificationToken);
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            model.addAttribute("userDto", userDto);

            return "passwordReset";
        }
        return "error";
    }

    @GetMapping("/resend/{action}")
    public String resend(@PathVariable String action, Model model) {
        model.addAttribute("action", action);
        return "resend";
    }

    @PostMapping("/resend/token")
    public String resendVerificationTokenPost(@RequestParam String email,
                                              Model model,
                                              WebRequest request) {
        User user = userService.findByEmail(email);

        if (user == null) {
            String message = "Taki email nie istnieje w naszej bazie danych";
            model.addAttribute("message", message);
            return "error";
        }

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnUserSelfActivation(user, request.getLocale(), appUrl));

        return "emailSendConfirmation";
    }

    @PostMapping("/resend/password")
    public String resendPasswordResetPost(@RequestParam String email,
                                Model model,
                                WebRequest request) {
        User user = userService.findByEmail(email);

        if (user == null) {
            String message = "Taki email nie istnieje w naszej bazie danych";
            model.addAttribute("message", message);
            return "error";
        }
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnPasswordResetEvent(user, request.getLocale(), appUrl));

        return "emailSendConfirmation";
    }

    @PostMapping("/reset/password")
    public String resetPasswordPost(@ModelAttribute("userDto") @Valid UserDto userDto,
                                   BindingResult result) {
        if (!userDto.samePassword()) {
            FieldError error = new FieldError("userDto", "password", "Hasła są nie takie same");
            result.addError(error);
            return "passwordReset";
        }
        User user = userService.findByEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user = userService.hashPassword(user);
        userService.saveUser(user);

        return "redirect:/login";
    }

}



