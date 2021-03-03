package ru.korovko.clinic.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korovko.clinic.entity.Session;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.exception.IncorrectConfirmationCodeException;
import ru.korovko.clinic.exception.UserAlreadyRegisteredException;
import ru.korovko.clinic.mapper.UserMapper;
import ru.korovko.clinic.security.dto.RegistrationFinishRequest;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;
import ru.korovko.clinic.security.dto.RestoreFinishRequest;
import ru.korovko.clinic.security.dto.RestoreStartRequest;
import ru.korovko.clinic.security.repository.UserRepository;
import ru.korovko.clinic.security.service.UserRegistrationService;
import ru.korovko.clinic.service.MailService;
import ru.korovko.clinic.service.SessionService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final MailService mailService;
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${spring.mail.sender.registrationTopic}")
    private String confirmRegistrationTopic;
    @Value("${spring.mail.sender.registrationText}")
    private String confirmRegistrationText;
    @Value("${spring.mail.sender.restorationTopic}")
    private String restorePasswordTopic;
    @Value("${spring.mail.sender.restorationText}")
    private String restorePasswordText;

    @Override
    @Transactional
    public RegistrationResponse registerStart(RegistrationStartRequest request) {
        User user;
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            user = byEmail.get();
            if (user.getIsActivated()) {
                throw new UserAlreadyRegisteredException("User with such email has already been registered");
            }
            userMapper.toUser(user, request);
        } else {
            user = userMapper.toUser(request);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setConfirmationCode(generateConfirmationCode());
        User saved = userRepository.save(user);

        mailService.sendMessage(saved.getEmail(), confirmRegistrationTopic, String.format(confirmRegistrationText, saved.getConfirmationCode()));
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    @Transactional
    @Override
    public RegistrationResponse registerFinish(RegistrationFinishRequest request, UUID sessionId) {
        Session userSession = sessionService.getById(sessionId);
        if (LocalDateTime.now().isAfter(userSession.getExpiresAt())) {
            throw new RuntimeException("Session with id " + sessionId + " expired");
        }
        User user = userSession.getUser();
        if (user.getConfirmationCode() == null || user.getIsActivated()) {
            throw new UserAlreadyRegisteredException("User has already been registered");
        }
        if (!user.getConfirmationCode().equals(request.getConfirmationCode())) {
            throw new IncorrectConfirmationCodeException("Confirmation code is incorrect");
        }
        user.setConfirmationCode(null);
        user.setIsActivated(true);
        userRepository.save(user);
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    @Transactional
    @Override
    public RegistrationResponse restoreStart(RestoreStartRequest request) {
        String email = request.getEmail();
        User userByEmail = getUserByEmail(email);
        userByEmail.setConfirmationCode(generateConfirmationCode());
        User savedUser = userRepository.save(userByEmail);

        mailService.sendMessage(email, restorePasswordTopic, String.format(restorePasswordText, savedUser.getConfirmationCode()));
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    @Override
    public RegistrationResponse restoreFinish(RestoreFinishRequest request, UUID sessionId) {
        Session userSession = sessionService.getById(sessionId);
        if (LocalDateTime.now().isAfter(userSession.getExpiresAt())) {
            throw new RuntimeException("Session with id " + sessionId + " expired");
        }
        User user = userSession.getUser();
        if (isNull(user.getConfirmationCode())) {
            throw new RuntimeException("User doesn't have confirmation code");
        }
        if (!user.getConfirmationCode().equals(request.getConfirmationCode())) {
            throw new IncorrectConfirmationCodeException("Confirmation code is incorrect");
        }
        user.setConfirmationCode(null);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return new RegistrationResponse().setRegistrationStatus(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email.trim())
                .orElseThrow(() -> new EntityNotFoundException("No user with such email: " + email));
    }

    private Integer generateConfirmationCode() {
        int minCode = 100_000;
        int maxCode = 999_999;
        return new Random().nextInt(maxCode - minCode) + minCode;
    }
}