package ru.korovko.clinic.security.controller;

import io.swagger.annotations.Api;
import org.springframework.web.servlet.view.RedirectView;
import ru.korovko.clinic.security.dto.*;

import javax.servlet.http.HttpServletResponse;

@Api
public interface AuthenticationController {

    RegistrationResponse registerUser(RegistrationStartRequest request);

    RedirectView registerFinish(String confirmationCode);

    RegistrationResponse restoreStart(RestoreStartRequest request);

    RedirectView restoreConfirm(String confirmationCode, HttpServletResponse response);

    RegistrationResponse restoreFinish(RegisterFinishRequest request, String userId);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    CurrentUserDto getCurrentUser();
}
