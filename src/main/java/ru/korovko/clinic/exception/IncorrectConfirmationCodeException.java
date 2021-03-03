package ru.korovko.clinic.exception;

public class IncorrectConfirmationCodeException extends RuntimeException {

    public IncorrectConfirmationCodeException(String message) {
        super(message);
    }
}
