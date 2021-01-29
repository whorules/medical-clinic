package ru.korovko.clinic.exception;

public class IncorrectActivationCodeException extends RuntimeException {

    public IncorrectActivationCodeException(String message) {
        super(message);
    }
}
