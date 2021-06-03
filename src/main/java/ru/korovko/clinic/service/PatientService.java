package ru.korovko.clinic.service;

import ru.korovko.clinic.dto.CreatePatientRequest;
import ru.korovko.clinic.dto.CreatePatientResponse;
import ru.korovko.clinic.entity.Patient;

import java.util.UUID;

public interface PatientService {

    Patient getById(UUID patientId);

    CreatePatientResponse create(CreatePatientRequest request, UUID doctorId);
}
