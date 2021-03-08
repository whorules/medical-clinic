package ru.korovko.clinic.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.exception.IncorrectConfirmationCodeException;
import ru.korovko.clinic.exception.UserAlreadyRegisteredException;
import ru.korovko.clinic.mapper.UserMapper;
import ru.korovko.clinic.security.dto.RegistrationFinishRequest;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;
import ru.korovko.clinic.security.dto.RestoreFinishRequest;
import ru.korovko.clinic.security.dto.RestoreStartRequest;
import ru.korovko.clinic.security.dto.UserAction;
import ru.korovko.clinic.security.repository.UserRepository;
import ru.korovko.clinic.security.service.UserRegistrationService;
import ru.korovko.clinic.service.MailService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${spring.mail.sender.registrationTopic}")
    private String registrationTopic;
    @Value("${spring.mail.sender.restorationTopic}")
    private String restorePasswordTopic;

    @Override
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

        String message = mailService.generateTextForMessage(saved.getConfirmationCode(), UserAction.REGISTRATION);
        mailService.sendMessage(saved.getEmail(), registrationTopic, message);
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    @Override
    public RegistrationResponse registerFinish(RegistrationFinishRequest request) {
        String confirmationCode = request.getConfirmationCode();
        Optional<User> optionalUser = userRepository.findByConfirmationCode(confirmationCode);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("No user with such confirmation code: " + confirmationCode);
        }
        User user = optionalUser.get();
        if (!user.getConfirmationCode().equals(confirmationCode)) {
            throw new IncorrectConfirmationCodeException("Confirmation code is incorrect");
        }
        user.setConfirmationCode(null);
        user.setIsActivated(true);
        userRepository.save(user);
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    @Override
    public RegistrationResponse restoreStart(RestoreStartRequest request) {
        String email = request.getEmail();
        User userByEmail = getUserByEmail(email);
        userByEmail.setConfirmationCode(generateConfirmationCode());
        User savedUser = userRepository.save(userByEmail);

        String message = mailService.generateTextForMessage(savedUser.getConfirmationCode(), UserAction.PASSWORD_RESTORATION);
        mailService.sendMessage(email, restorePasswordTopic, message);
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.SUCCESS);
    }

    @Override
    public RegistrationResponse restoreFinish(RestoreFinishRequest request) {
        String confirmationCode = request.getConfirmationCode();
        Optional<User> optionalUser = userRepository.findByConfirmationCode(confirmationCode);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("No user with such confirmation code: " + confirmationCode);
        }
        User user = optionalUser.get();
        if (!user.getConfirmationCode().equals(confirmationCode)) {
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

    private String generateConfirmationCode() {
        return UUID.randomUUID().toString();
    }
}