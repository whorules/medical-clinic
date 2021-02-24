package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticationRequest {

    private String email;
    private String password;
}
