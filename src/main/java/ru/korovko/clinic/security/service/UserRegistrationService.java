package ru.korovko.clinic.security.service;

import ru.korovko.clinic.security.dto.RegisterFinishRequest;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;
import ru.korovko.clinic.security.dto.RestoreStartRequest;

import java.util.UUID;

public interface UserRegistrationService {

    RegistrationResponse registerStart(RegistrationStartRequest request);

    void registerFinish(String confirmationCode);

    RegistrationResponse restoreStart(RestoreStartRequest request);

    UUID restoreConfirm(String confirmCode);

    void restoreFinish(RegisterFinishRequest request, String userId);
}
