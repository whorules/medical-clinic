package ru.korovko.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.korovko.clinic.service.MailService;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.sender.username}")
    private String username;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMessage(String address, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(address);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
