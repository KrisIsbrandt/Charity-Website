package pl.coderslab.charity.services;

public interface EmailService {

    void sendEmail(String sendTo, String subject, String content);
}
