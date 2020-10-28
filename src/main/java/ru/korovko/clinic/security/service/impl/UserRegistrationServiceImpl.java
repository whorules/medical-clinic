package ru.korovko.clinic.security.service.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public RegistrationResponse registerNewUser(UserRegistrationRequest registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.getEmail().trim()).isPresent()) {
            throw new UserAlreadyRegisteredException("User with such email has already been registered");
        }
        User user = userMapper.toUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userRepository.save(user);
        return new RegistrationResponse(RegistrationResponse.RegistrationStatus.SUCCESS);
    }
}