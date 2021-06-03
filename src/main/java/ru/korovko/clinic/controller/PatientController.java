package ru.korovko.clinic.controller;

import io.swagger.annotations.Api;
import ru.korovko.clinic.dto.CreatePatientRequest;
import ru.korovko.clinic.dto.CreatePatientResponse;

@Api
public interface PatientController {

    CreatePatientResponse createPatient(CreatePatientRequest request);
}