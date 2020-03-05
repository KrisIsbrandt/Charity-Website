package pl.coderslab.charity.services.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.services.EmailService;
import pl.coderslab.charity.services.user.UserService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public RegistrationListener(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

//        String confirmationUrl = event.getAppUrl() + "/register/confirm/" + token;
        String confirmationUrl = "http://localhost:8080/register/confirm/" + token;

        emailService.sendVerificationEmail(user, confirmationUrl);
    }
}
