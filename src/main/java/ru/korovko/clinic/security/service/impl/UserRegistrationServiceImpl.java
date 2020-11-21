package ru.korovko.clinic.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.exception.IncorrectActivationTokenException;
import ru.korovko.clinic.exception.UserAlreadyRegisteredException;
import ru.korovko.clinic.mapper.UserMapper;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.UserRegistrationRequest;
import ru.korovko.clinic.security.repository.UserRepository;
import ru.korovko.clinic.security.service.UserRegistrationService;
import ru.korovko.clinic.service.MailService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${spring.mail.sender.topic}")
    private String confirmRegistrationTopic;
    @Value("${spring.mail.sender.text}")
    private String confirmRegistrationText;
    @Value("${app.security.confirmRegistrationAddress}")
    private String confirmRegistrationAddress;

    @Override
    @Transactional
    public RegistrationResponse registerNewUser(UserRegistrationRequest registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.getEmail().trim()).isPresent()) {
            throw new UserAlreadyRegisteredException("User with such email has already been registered");
        }
        User user = userMapper.toUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setActivationToken(generateActivationToken());
        User saved = userRepository.save(user);
        mailService.sendMessage(saved.getEmail(), confirmRegistrationTopic,
                String.format(confirmRegistrationText, confirmRegistrationAddress + saved.getActivationToken()));
        return new RegistrationResponse(RegistrationResponse.RegistrationStatus.SUCCESS); // todo success?
    }

    @Transactional
    @Override
    public RegistrationResponse registerConfirm(String token) {
        Optional<User> optionalUser = userRepository.findByActivationToken(token);
        if (optionalUser.isEmpty()) {
            log.error("Activation token not found");
            throw new IncorrectActivationTokenException("Activation token not found");
        } else {
            User user = optionalUser.get();
            user.setActivationToken(null);
            user.setIsActivated(true);
            userRepository.save(user);
        }
        return new RegistrationResponse(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    private String generateActivationToken() {
        return UUID.randomUUID().toString();
    }
}