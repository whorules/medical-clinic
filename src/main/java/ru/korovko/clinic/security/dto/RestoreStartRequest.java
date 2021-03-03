package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.validation.EmailValidation;

@Data
@Accessors(chain = true)
public class RestoreStartRequest {

    @EmailValidation
    private String email;
}
