package ru.korovko.clinic.service.impl;

import org.springframework.stereotype.Service;
import ru.korovko.clinic.dto.CreatePatientRequest;
import ru.korovko.clinic.service.PatientService;

import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    @Override
    public void create(CreatePatientRequest request, UUID id) {

    }
}
