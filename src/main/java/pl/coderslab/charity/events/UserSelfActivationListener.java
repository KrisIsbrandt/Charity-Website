package pl.coderslab.charity.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.entities.User;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.services.EmailService;
import pl.coderslab.charity.services.UserService;

import java.util.List;
import java.util.UUID;

import static pl.coderslab.charity.entities.VerificationToken.Type.ACCOUNT_ACTIVATION;

@Component
public class UserSelfActivationListener implements ApplicationListener<OnUserSelfActivation> {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserSelfActivationListener(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnUserSelfActivation event) {
        this.userSelfActivationHandler(event);
    }

    private void userSelfActivationHandler(OnUserSelfActivation event) {
        User user = event.getUser();
        List<VerificationToken> tokens = userService.findAllTokensByUserAndType(user, ACCOUNT_ACTIVATION);
        tokens.forEach(userService::expireVerificationToken);

        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token, ACCOUNT_ACTIVATION);

        String confirmationUrl = event.getAppUrl() + "/confirm/" + token;
        emailService.sendVerificationEmail(user, confirmationUrl);
    }
}