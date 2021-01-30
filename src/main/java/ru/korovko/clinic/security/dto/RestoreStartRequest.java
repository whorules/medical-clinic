package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.annotation.EmailValidation;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RestoreStartRequest implements Serializable {

    @EmailValidation
    private String email;
}
