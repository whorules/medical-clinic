package ru.korovko.clinic.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.UserRegistrationRequest;
import ru.korovko.clinic.security.model.Role;
import ru.korovko.clinic.security.model.User;
import ru.korovko.clinic.security.repository.RoleRepository;
import ru.korovko.clinic.security.repository.UserRepository;
import ru.korovko.clinic.security.service.UserRegistrationService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegistrationResponse registerNewUser(UserRegistrationRequest registrationRequest) {
//        if (userRepository.findByUserName(registrationRequest.getUserName()).isPresent()) {
//            throw new RuntimeException(); //TODO change
//        }
        User user = new User()
                .setUserName(registrationRequest.getUserName())
                .setPassword(passwordEncoder.encode(registrationRequest.getPassword()))
                .setRoles(Collections.singleton(roleRepository.findByName(Role.ROLE_USER)))
                .setEnabled(true);

        userRepository.save(user);

        return new RegistrationResponse(RegistrationResponse.RegistrationStatus.SUCCESS);
    }
}
