package ru.korovko.clinic.service;

import ru.korovko.clinic.entity.Session;

import java.util.UUID;

public interface SessionService {

    String create(String email);

    Session getById(UUID id);
}
