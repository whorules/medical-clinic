package ru.korovko.clinic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    private LocalDateTime statusChangedAt;
    @ManyToOne
    private User cancelledBy;
    private String cancellationReason;
}
