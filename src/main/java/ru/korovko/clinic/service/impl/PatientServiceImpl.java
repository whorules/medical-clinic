package ru.korovko.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.korovko.clinic.dto.CreatePatientRequest;
import ru.korovko.clinic.dto.CreatePatientResponse;
import ru.korovko.clinic.entity.Patient;
import ru.korovko.clinic.entity.PatientStatus;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.mapper.PatientMapper;
import ru.korovko.clinic.repository.PatientRepository;
import ru.korovko.clinic.service.PatientService;
import ru.korovko.clinic.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientMapper mapper;
    private final UserService userService;
    private final PatientRepository repository;

    @Override
    public Patient getById(UUID patientId) {
        return repository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with such id does not exist"));
    }

    @Override
    public CreatePatientResponse create(CreatePatientRequest request, UUID doctorId) {
        User doctor = userService.getById(doctorId);
        Patient patient = mapper.toPatient(request, doctor);
        patient.setStatus(PatientStatus.TREATMENT);
        return mapper.toCreatePatientResponse(repository.save(patient));
    }
}
