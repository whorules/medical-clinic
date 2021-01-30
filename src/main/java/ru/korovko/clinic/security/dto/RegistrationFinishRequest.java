package ru.korovko.clinic.security.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegistrationFinishRequest implements Serializable {

    private String email;
    private Integer confirmationCode;
}
