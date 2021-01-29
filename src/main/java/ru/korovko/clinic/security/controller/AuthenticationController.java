package ru.korovko.clinic.security.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korovko.clinic.security.dto.AuthenticationRequest;
import ru.korovko.clinic.security.dto.AuthenticationResponse;
import ru.korovko.clinic.security.dto.CurrentUserDto;
import ru.korovko.clinic.security.dto.RegistrationFinishRequest;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;
import ru.korovko.clinic.security.dto.RestoreFinishRequest;
import ru.korovko.clinic.security.dto.RestoreStartRequest;
import ru.korovko.clinic.security.dto.UserPrincipal;
import ru.korovko.clinic.security.service.AuthenticationService;
import ru.korovko.clinic.security.service.UserRegistrationService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping(value = "/register-start")
    public RegistrationResponse registerUser(@Valid @RequestBody RegistrationStartRequest request) {
        return userRegistrationService.registerStart(request);
    }

    @PostMapping("/register-finish")
    public RegistrationResponse registerConfirm(@RequestBody RegistrationFinishRequest request) {
        return userRegistrationService.registerFinish(request);
    }

    @PostMapping("/restore-start")
    public RegistrationResponse restoreStart(@RequestBody RestoreStartRequest request) {
        return userRegistrationService.restoreStart(request);
    }

    @PostMapping("/restore-finish")
    public RegistrationResponse restoreFinish(@RequestBody RestoreFinishRequest request) {
        return userRegistrationService.restoreFinish(request);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return new AuthenticationResponse().setToken(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public CurrentUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return new CurrentUserDto().setLogin(principal.getUsername())
                .setAuthorities(principal.getAuthorities().stream()
                        .map(o -> new SimpleGrantedAuthority(o.getAuthority())).collect(Collectors.toSet()));
    }

    @PostMapping("/logout")
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
