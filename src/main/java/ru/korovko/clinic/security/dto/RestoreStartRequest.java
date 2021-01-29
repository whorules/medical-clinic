package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.annotation.EmailValidation;

@Data
@Accessors(chain = true)
public class RestoreStartRequest {

    @EmailValidation
    private String email;
}
