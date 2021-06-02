package ru.korovko.clinic.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.korovko.clinic.entity.PatientStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class CreatePatientResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String diagnosis;
    private Integer socialSecurityNumber;
    private PatientStatus status;
    private LocalDateTime registeredAt;
}
