package ru.korovko.clinic.repository;

import ru.korovko.clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegisteredUserRepository extends JpaRepository<User, UUID> {
}
