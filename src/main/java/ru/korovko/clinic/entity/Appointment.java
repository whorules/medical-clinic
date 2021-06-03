package ru.korovko.clinic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @Enumerated(EnumType.STRING)
    private String name;
    private AppointmentType type;
    private LocalDateTime createdAt;
    @ManyToOne
    private User createdBy;
    @OneToMany(mappedBy = "appointment")
    private List<Event> events;
}
