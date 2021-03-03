package ru.korovko.clinic.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.validation.EmailValidation;

@Data
@Accessors(chain = true)
public class RestoreStartRequest {

    @EmailValidation
    @ApiModelProperty(example = "youremail@mail.com")
    private String email;
}
