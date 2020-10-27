package ru.korovko.clinic.security.exception;

public class InvalidTokenDataException extends RuntimeException {

    public InvalidTokenDataException(String message) {
        super(message);
    }
}
