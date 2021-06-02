package ru.korovko.clinic.service;

import ru.korovko.clinic.dto.CreatePatientRequest;
import ru.korovko.clinic.dto.CreatePatientResponse;

import java.util.UUID;

public interface PatientService {

    CreatePatientResponse create(CreatePatientRequest request, UUID doctorId);
}
