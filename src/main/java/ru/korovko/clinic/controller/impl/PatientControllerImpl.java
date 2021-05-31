package ru.korovko.clinic.controller.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korovko.clinic.controller.PatientController;

@RestController
@RequestMapping("/patients")
public class PatientControllerImpl implements PatientController {

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public void createPatient() {

    }
}
