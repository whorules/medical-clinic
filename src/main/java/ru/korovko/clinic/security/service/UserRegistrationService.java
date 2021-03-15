package ru.korovko.clinic.security.service;

import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;
import ru.korovko.clinic.security.dto.RestoreFinishRequest;
import ru.korovko.clinic.security.dto.RestoreStartRequest;

public interface UserRegistrationService {

    RegistrationResponse registerStart(RegistrationStartRequest request);

    RegistrationResponse registerFinish(String confirmationCode);

    RegistrationResponse restoreStart(RestoreStartRequest request);

    RegistrationResponse restoreFinish(RestoreFinishRequest request);
}
