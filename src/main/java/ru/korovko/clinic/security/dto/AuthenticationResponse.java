package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationResponse {

    private AuthenticationStatus status;
    private String token;
    private String message;

    public enum AuthenticationStatus {
        SUCCESS, FAIL
    }
}
