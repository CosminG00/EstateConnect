package cms.backend.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.frontend.url}")
    private String frontendUrl;

    private final JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        this.mailSender.send(message);
    }

    @Async
    public void sendClientActivationEmail(String to, String token) {
        this.logger.info("Sending client activation email to {}", to);
        String subject = "[%s] Activation account".formatted(this.appName);
        String content = """
            To activate your account, please click on the link below:
            %s/activate-client?token=%s
        """.formatted(this.frontendUrl, token);
        this.sendEmail(to, subject, content);
    }

    @Async
    public void sendAgencyActivationEmail(String to, String token) {
        this.logger.info("Sending client activation email to {}", to);
        String subject = "[%s] Activation account".formatted(this.appName);
        String content = """
            To activate your account, please click on the link below:
            %s/activate-agency?token=%s
        """.formatted(this.frontendUrl, token);
        this.sendEmail(to, subject, content);
    }

    @Async
    public void sendResetPasswordEmail(String to, String token) {
        this.logger.info("Sending reset password email to {}", to);
        String subject = "[%s] Reset password".formatted(this.appName);
        String content = """
            To reset your password, please click on the link below:
            %s/reset-password?token=%s
        """.formatted(this.frontendUrl, token);
        this.sendEmail(to, subject, content);
    }

    @Async
    public void sendChangeEmail(String to, String token) {
        this.logger.info("Sending change email to {}", to);
        String subject = "[%s] Change email".formatted(this.appName);
        String content = """
            To change your email, please click on the link below:
            %s/change-email?token=%s
        """.formatted(this.frontendUrl, token);
        this.sendEmail(to, subject, content);
    }

    @Async
    public void sendChangePassword(String to, String token) {
        this.logger.info("Sending change password email to {}", to);
        String subject = "[%s] Change password".formatted(this.appName);
        String content = """
            To change your password, please click on the link below:
            %s/change-password?token=%s
        """.formatted(this.frontendUrl, token);
        this.sendEmail(to, subject, content);
    }
}
