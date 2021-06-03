package ru.korovko.clinic.dto.pattern;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents pattern of an appointment for creating events.
 * Using this pattern we create specific count of an appointments in specific period of time
 * @example 2 times (countPerTimeUnit) in a DAY (perTimeUnit) for 2 (duration) a WEEK (inTimeUnit)
 */
@Data
public class AppointmentPattern {

    private LocalDateTime startDate;
    private Integer countPerTimeUnit;
    private AppointmentTimeUnit perTimeUnit;
    private Integer duration;
    private AppointmentTimeUnit inTimeUnit;
}
