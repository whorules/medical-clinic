package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.validation.PasswordValidation;

@Data
@Accessors(chain = true)
public class RegisterFinishRequest {

    @PasswordValidation
    private String newPassword;
}
