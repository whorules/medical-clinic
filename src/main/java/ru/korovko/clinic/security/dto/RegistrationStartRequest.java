package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.annotation.EmailValidation;
import ru.korovko.clinic.annotation.PasswordValidation;
import ru.korovko.clinic.entity.Speciality;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class RegistrationStartRequest {

    @NotBlank(message = "Should not be null")
    private String firstName;
    @NotBlank(message = "Should not be null")
    private String lastName;
    private Speciality specialty;
    @NotBlank(message = "Should not be null")
    @EmailValidation
    private String email;
    @NotBlank(message = "Should not be null")
    @PasswordValidation
    private String password;
}