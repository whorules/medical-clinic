package ru.korovko.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korovko.clinic.entity.Patient;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

}
