package ru.korovko.clinic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePatientRequest {

    @ApiModelProperty(example = "Denis")
    private String firstName;
    @ApiModelProperty(example = "Smith")
    private String lastName;
    @ApiModelProperty(example = "1953-09-30")
    private LocalDate dateOfBirth;
    @ApiModelProperty(example = "Angina")
    private String diagnosis;
    @ApiModelProperty(example = "1900986478")
    private Integer socialSecurityNumber;
}
