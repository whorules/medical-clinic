package ru.korovko.clinic.security.dto;

import lombok.Data;

@Data
public class RegistrationFinishRequest {

    private Integer confirmationCode;
}
