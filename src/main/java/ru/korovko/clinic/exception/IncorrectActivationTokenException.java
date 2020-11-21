package ru.korovko.clinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, value = HttpStatus.BAD_REQUEST)
public class IncorrectActivationTokenException extends RuntimeException {

    public IncorrectActivationTokenException(String message) {
        super(message);
    }
}
