package pl.coderslab.charity.services;

import pl.coderslab.charity.entities.User;

public interface EmailService {

    void sendEmail(String sendTo, String subject, String content);

    void sendVerificationEmail(User user, String token);

    void sendResetPasswordEmail(User user, String token);
}
