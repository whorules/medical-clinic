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
import ru.korovko.clinic.security.dto.RegisterFinishRequest;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;
import ru.korovko.clinic.security.dto.RestoreStartRequest;
import ru.korovko.clinic.security.dto.UserAction;
import ru.korovko.clinic.security.repository.UserRepository;
import ru.korovko.clinic.security.service.UserRegistrationService;
import ru.korovko.clinic.service.MailService;

import javax.persistence.EntityNotFoundException;
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
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user != null) {
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
    public void registerFinish(String confirmationCode) {
        User user = userRepository.findByConfirmationCode(confirmationCode)
                .orElseThrow(() -> new EntityNotFoundException("No user with such confirmation code: " + confirmationCode));
        if (!user.getConfirmationCode().equals(confirmationCode)) {
            throw new IncorrectConfirmationCodeException("Confirmation code is incorrect");
        }
        user.setConfirmationCode(null);
        user.setIsActivated(true);
        userRepository.save(user);
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
    public UUID restoreConfirm(String confirmCode) {
        User user = userRepository.findByConfirmationCode(confirmCode).orElseThrow(() -> new EntityNotFoundException("No user with such confirm code"));
        return user.getId();
    }

    @Override
    public void restoreFinish(RegisterFinishRequest request, String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new EntityNotFoundException("No user with such id: " + userId));
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        if (passwordEncoder.matches(user.getPassword(), encodedNewPassword)) {
            throw new RuntimeException("Use new password");
        }
        user.setPassword(encodedNewPassword);
        user.setConfirmationCode(null);
        userRepository.save(user);
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