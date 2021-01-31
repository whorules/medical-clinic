package ru.korovko.clinic.security.dto;

import lombok.Data;

@Data
public class RegistrationFinishRequest {

    private String email;
    private Integer confirmationCode;
}
