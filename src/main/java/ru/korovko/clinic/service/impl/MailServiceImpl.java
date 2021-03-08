package ru.korovko.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.korovko.clinic.security.dto.UserAction;
import ru.korovko.clinic.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.sender.username}")
    private String username;
    @Value("${spring.mail.sender.registrationText}")
    private String confirmRegistrationText;
    @Value("${spring.mail.sender.restorationText}")
    private String restorePasswordText;
    @Value("${spring.mail.sender.registrationLink}")
    private String confirmRegistrationLink;
    @Value("${spring.mail.sender.restorationLink}")
    private String restorePasswordLink;
    @Value("${spring.mail.sender.htmlLayout}")
    private String htmlLayout;

    @Override
    public void sendMessage(String address, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setFrom(username);
            helper.setTo(address);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException ex) {
            log.error("Error occurred while sending message with confirmation code", ex);
            // todo throw
        }
        javaMailSender.send(mimeMessage);
    }

    @Override
    public String generateTextForMessage(String confirmCode, UserAction action) {
        String text;
        String link;
        if (action == UserAction.REGISTRATION) {
            link = String.format(confirmRegistrationLink, confirmCode);
            text = String.format(confirmRegistrationText, link);
        } else {
            link = String.format(restorePasswordLink, confirmCode);
            text = String.format(restorePasswordText, link);
        }
        return String.format(htmlLayout, text);
    }
}