package ru.korovko.clinic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Patient {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String diagnosis;
    private Integer socialSecurityNumber;
    @Enumerated(EnumType.STRING)
    private PatientStatus status;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
    @CreationTimestamp
    private LocalDateTime registeredAt;
    private LocalDateTime dischargedAt;
}