package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationResponse {

    private RegistrationStatus registrationStatus;
    private String message;

    public enum RegistrationStatus {
        SUCCESS, FAIL
    }
}
