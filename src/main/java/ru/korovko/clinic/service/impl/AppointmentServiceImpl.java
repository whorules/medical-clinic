package ru.korovko.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.korovko.clinic.dto.CreateAppointmentRequest;
import ru.korovko.clinic.dto.CreateAppointmentResponse;
import ru.korovko.clinic.entity.Patient;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.service.AppointmentService;
import ru.korovko.clinic.service.PatientService;
import ru.korovko.clinic.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final PatientService patientService;
    private final UserService userService;

    @Override
    public CreateAppointmentResponse create(CreateAppointmentRequest request, UUID patientId, UUID doctorId) {
        User doctor = userService.getById(doctorId);
        Patient patient = patientService.getById(patientId);


        return null;
    }
}
