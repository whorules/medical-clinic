package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class RegistrationResponse {

    private final RegistrationStatus registrationStatus;

    public enum RegistrationStatus {
        SUCCESS, FAIL
    }
}
