package ru.korovko.clinic.security.service;

import ru.korovko.clinic.security.dto.AuthenticationRequest;

public interface AuthenticationService {

    String authenticate(AuthenticationRequest authenticationRequest);
}
