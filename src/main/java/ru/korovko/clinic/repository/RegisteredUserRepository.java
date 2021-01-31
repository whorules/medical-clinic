package ru.korovko.clinic.repository;

import ru.korovko.clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegisteredUserRepository extends JpaRepository<User, UUID> {
}
