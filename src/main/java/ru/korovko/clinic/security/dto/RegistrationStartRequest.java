package ru.korovko.clinic.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.korovko.clinic.annotation.EmailValidation;
import ru.korovko.clinic.annotation.PasswordValidation;
import ru.korovko.clinic.entity.Speciality;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationStartRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private Speciality speciality;
    @NotBlank
    @EmailValidation
    private String email;
    @PasswordValidation
    private String password;
}
