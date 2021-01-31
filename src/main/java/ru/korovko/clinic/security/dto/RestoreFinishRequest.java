package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.annotation.EmailValidation;
import ru.korovko.clinic.annotation.PasswordValidation;

@Data
@Accessors(chain = true)
public class RestoreFinishRequest {

    @EmailValidation
    private String email;
    private String confirmationCode;
    @PasswordValidation
    private String password;
    @PasswordValidation
    private String passwordConfirm;
}
