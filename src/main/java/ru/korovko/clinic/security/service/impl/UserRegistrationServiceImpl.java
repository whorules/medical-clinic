package ru.korovko.clinic.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.exception.UserAlreadyRegisteredException;
import ru.korovko.clinic.mapper.UserMapper;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.UserRegistrationRequest;
import ru.korovko.clinic.security.repository.UserRepository;
import ru.korovko.clinic.security.service.UserRegistrationService;
import ru.korovko.clinic.service.MailService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private static final Integer ACTIVATION_CODE_LENGTH = 6;

    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${spring.mail.sender.topic}")
    private String confirmRegistrationTopic;
    @Value("${spring.mail.sender.text}")
    private String confirmRegistrationText;

    @Override
    @Transactional
    public RegistrationResponse registerNewUser(UserRegistrationRequest registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.getEmail().trim()).isPresent()) {
            throw new UserAlreadyRegisteredException("User with such email has already been registered");
        }
        User user = userMapper.toUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setActivationCode(generateActivationCode());
        User saved = userRepository.save(user);
        mailService.sendMessage(saved.getEmail(), confirmRegistrationTopic, String.format(confirmRegistrationText, saved.getActivationCode()));
        return new RegistrationResponse(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    private Integer generateActivationCode() {
        int minCodeValue = (int) Math.pow(10, ACTIVATION_CODE_LENGTH - 1);
        return minCodeValue + new Random().nextInt(9 * minCodeValue);
    }
}