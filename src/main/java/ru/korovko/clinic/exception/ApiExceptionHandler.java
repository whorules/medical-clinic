package ru.korovko.clinic.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.korovko.clinic.security.dto.RegistrationResponse;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ UserAlreadyRegisteredException.class, IncorrectActivationCodeException.class })
    @ResponseBody
    public RegistrationResponse handleUserAlreadyExist(UserAlreadyRegisteredException ex) {
        String message = ex.getMessage();
        return new RegistrationResponse()
                .setRegistrationStatus(RegistrationResponse.RegistrationStatus.FAIL)
                .setMessage(message);
    }
}
