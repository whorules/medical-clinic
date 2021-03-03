package ru.korovko.clinic.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.validation.PasswordValidation;

@Data
@Accessors(chain = true)
public class RestoreFinishRequest {

    @ApiModelProperty(example = "123456")
    private Integer confirmationCode;

    @PasswordValidation
    @ApiModelProperty(example = "YourPassword1.")
    private String password;
}
