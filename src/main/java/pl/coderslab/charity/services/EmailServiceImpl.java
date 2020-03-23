package pl.coderslab.charity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.coderslab.charity.entities.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService  {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String applicationEmailAddress;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(String sendTo, String subject, String content) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(sendTo);
            helper.setReplyTo(applicationEmailAddress);
            helper.setFrom(applicationEmailAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }

    @Override
    public void sendResetPasswordEmail(User user, String confirmationUrl) {
        Context ctx = new Context();
        ctx.setVariable("firstName", user.getFirstName());
        ctx.setVariable("confirmationUrl", confirmationUrl);
        String body = templateEngine.process("passwordReset.html", ctx);

        sendEmail(user.getEmail(), "Password Reset", body);
    }

    @Override
    public void sendVerificationEmail(User user, String confirmationUrl) {
        Context ctx = new Context();
        ctx.setVariable("firstName", user.getFirstName());
        ctx.setVariable("confirmationUrl", confirmationUrl);
        String body = templateEngine.process("registration.html", ctx);

        sendEmail(user.getEmail(), "Activate your account", body);
    }


}
