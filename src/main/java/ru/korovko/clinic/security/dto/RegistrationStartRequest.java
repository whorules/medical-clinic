package ru.korovko.clinic.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.validation.EmailValidation;
import ru.korovko.clinic.validation.PasswordValidation;
import ru.korovko.clinic.entity.Speciality;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class RegistrationStartRequest {

    @NotBlank(message = "Should not be null")
    @ApiModelProperty(example = "Tom")
    private String firstName;
    
    @NotBlank(message = "Should not be null")
    @ApiModelProperty(example = "Smith")
    private String lastName;

    @ApiModelProperty(example = "DOCTOR")
    private Speciality specialty;

    @NotBlank(message = "Should not be null")
    @EmailValidation
    @ApiModelProperty(example = "youremail@mail.com")
    private String email;

    @NotBlank(message = "Should not be null")
    @PasswordValidation
    @ApiModelProperty(example = "YourPassword1.")
    private String password;
}