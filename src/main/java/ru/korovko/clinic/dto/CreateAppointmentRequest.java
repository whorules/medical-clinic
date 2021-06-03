package ru.korovko.clinic.dto;

import lombok.Data;
import ru.korovko.clinic.dto.pattern.AppointmentPattern;
import ru.korovko.clinic.entity.AppointmentType;

@Data
public class CreateAppointmentRequest {

    private String name;
    private AppointmentPattern pattern;
    private AppointmentType type;
}
