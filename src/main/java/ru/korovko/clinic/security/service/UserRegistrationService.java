package ru.korovko.clinic.security.service;

import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.UserRegistrationRequest;

public interface UserRegistrationService {

    RegistrationResponse registerNewUser(UserRegistrationRequest registrationRequest);
}
