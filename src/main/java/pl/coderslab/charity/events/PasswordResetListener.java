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

import static pl.coderslab.charity.entities.VerificationToken.Type.PASSWORD_RESET;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public PasswordResetListener(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnPasswordResetEvent event) {
        this.resetPasswordHandler(event);
    }

    private void resetPasswordHandler(OnPasswordResetEvent event) {
        User user = event.getUser();
        List<VerificationToken> tokens = userService.findAllTokensByUserAndType(user, PASSWORD_RESET);
        tokens.forEach(userService::expireVerificationToken);

        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token, PASSWORD_RESET);

        String confirmationUrl = event.getAppUrl() + "/confirm/" + token;
        emailService.sendResetPasswordEmail(user, confirmationUrl);
    }
}
