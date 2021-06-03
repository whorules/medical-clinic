package ru.korovko.clinic.controller;

import io.swagger.annotations.Api;
import ru.korovko.clinic.dto.CreateAppointmentRequest;
import ru.korovko.clinic.dto.CreateAppointmentResponse;

import java.util.UUID;

@Api
public interface AppointmentController {

    CreateAppointmentResponse create(CreateAppointmentRequest request, UUID userId);
}
