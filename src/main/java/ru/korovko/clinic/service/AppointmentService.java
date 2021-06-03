package ru.korovko.clinic.service;

import ru.korovko.clinic.dto.CreateAppointmentRequest;
import ru.korovko.clinic.dto.CreateAppointmentResponse;

import java.util.UUID;

public interface AppointmentService {

    CreateAppointmentResponse create(CreateAppointmentRequest request, UUID patientId, UUID doctorId);
}
