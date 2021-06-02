package ru.korovko.clinic.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.entity.PatientStatus;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class CreatePatientRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String diagnosis;
    private Integer socialSecurityNumber;
    private PatientStatus status;
}
