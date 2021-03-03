package ru.korovko.clinic.configuration.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailProperties {

    @Value("${spring.mail.sender.host}")
    private String host;
    @Value("${spring.mail.sender.port}")
    private int port;
    @Value("${spring.mail.sender.username}")
    private String username;
    @Value("${spring.mail.sender.password}")
    private String password;
    @Value("${spring.mail.sender.protocol}")
    private String protocol;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", "true");
        return mailSender;
    }
}
