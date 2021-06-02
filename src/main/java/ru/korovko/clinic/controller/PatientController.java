package ru.korovko.clinic.controller;

import io.swagger.annotations.Api;
import ru.korovko.clinic.dto.CreatePatientRequest;

@Api
public interface PatientController {

    void createPatient(CreatePatientRequest request);
}