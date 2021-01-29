package ru.korovko.clinic.security.service;

import ru.korovko.clinic.security.dto.RegistrationFinishRequest;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;

public interface UserRegistrationService {

    RegistrationResponse registerStart(RegistrationStartRequest request);

    RegistrationResponse registerFinish(RegistrationFinishRequest request);
}
