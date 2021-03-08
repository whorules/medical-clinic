package ru.korovko.clinic.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.validation.PasswordValidation;

@Data
@Accessors(chain = true)
public class RestoreFinishRequest {

    @ApiModelProperty(example = "6644e0b3-78c0-41bc-b639-7e8d42fec2cf")
    private String confirmationCode;

    @PasswordValidation
    @ApiModelProperty(example = "YourPassword1.")
    private String password;
}
