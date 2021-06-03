package ru.korovko.clinic.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.korovko.clinic.controller.AppointmentController;
import ru.korovko.clinic.dto.CreateAppointmentRequest;
import ru.korovko.clinic.dto.CreateAppointmentResponse;
import ru.korovko.clinic.security.dto.CurrentUserDto;
import ru.korovko.clinic.security.utils.AuthenticationResolver;
import ru.korovko.clinic.service.AppointmentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("patients/{patientId}/appointments")
public class AppointmentControllerImpl implements AppointmentController {

    private final AppointmentService service;
    private final AuthenticationResolver authenticationResolver;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public CreateAppointmentResponse create(@RequestBody CreateAppointmentRequest request, @PathVariable UUID patientId) {
        CurrentUserDto doctor = authenticationResolver.getCurrentUser();
        return service.create(request, patientId, doctor.getId());
    }
}
