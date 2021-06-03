package ru.korovko.clinic.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korovko.clinic.controller.PatientController;
import ru.korovko.clinic.dto.CreatePatientRequest;
import ru.korovko.clinic.dto.CreatePatientResponse;
import ru.korovko.clinic.security.dto.CurrentUserDto;
import ru.korovko.clinic.security.utils.AuthenticationResolver;
import ru.korovko.clinic.service.PatientService;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientControllerImpl implements PatientController {

    private final PatientService patientService;
    private final AuthenticationResolver authenticationResolver;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public CreatePatientResponse createPatient(@RequestBody CreatePatientRequest request) {
        CurrentUserDto currentUser = authenticationResolver.getCurrentUser();
        return patientService.create(request, currentUser.getId());
    }
}
