package ru.korovko.clinic.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegistrationFinishRequest {

    @ApiModelProperty(example = "123456")
    private Integer confirmationCode;
}
