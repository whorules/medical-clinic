package ru.korovko.clinic.service;

import ru.korovko.clinic.dto.CreatePatientRequest;

import java.util.UUID;

public interface PatientService {

    void create(CreatePatientRequest request, UUID doctorId);
}
