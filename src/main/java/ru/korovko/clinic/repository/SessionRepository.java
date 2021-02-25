package ru.korovko.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korovko.clinic.entity.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByUserId(UUID userId);
}
