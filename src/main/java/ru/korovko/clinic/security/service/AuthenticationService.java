package ru.korovko.clinic.security.service;

public interface AuthenticationService {

    String authenticate(String userName, String password);
}
