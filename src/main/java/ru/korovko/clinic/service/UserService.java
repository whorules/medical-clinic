package ru.korovko.clinic.service;

import ru.korovko.clinic.entity.User;

import java.util.UUID;

public interface UserService {

    User getById(UUID userId);
}
