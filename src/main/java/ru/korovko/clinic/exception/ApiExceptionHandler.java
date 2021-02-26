package ru.korovko.clinic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.korovko.clinic.dto.ValidationResult;
import ru.korovko.clinic.security.dto.AuthenticationResponse;
import ru.korovko.clinic.security.dto.RegistrationResponse;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public RegistrationResponse handleUserAlreadyExist(UserAlreadyRegisteredException ex) {
        String message = ex.getMessage();
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.FAIL)
                .setMessage(message);
    }

    @ExceptionHandler(IncorrectActivationCodeException.class)
    public RegistrationResponse handleIncorrectActivationCode(IncorrectActivationCodeException ex) {
        String message = ex.getMessage();
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.FAIL)
                .setMessage(message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public AuthenticationResponse handleBadCredentials(BadCredentialsException ex) {
        String message = ex.getMessage();
        return new AuthenticationResponse()
                .setStatus(AuthenticationResponse.AuthenticationStatus.FAIL)
                .setMessage(message);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ValidationResult> messages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationResult()
                        .setField(fieldError.getField())
                        .setMessage(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
    }
}
