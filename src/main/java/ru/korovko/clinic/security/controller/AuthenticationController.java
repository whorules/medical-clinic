package ru.korovko.clinic.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.korovko.clinic.security.dto.AuthenticationRequest;
import ru.korovko.clinic.security.dto.AuthenticationResponse;
import ru.korovko.clinic.security.dto.CurrentUserDto;
import ru.korovko.clinic.security.dto.RegistrationResponse;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;
import ru.korovko.clinic.security.dto.RestoreStartRequest;
import ru.korovko.clinic.security.dto.UserPrincipal;
import ru.korovko.clinic.security.service.AuthenticationService;
import ru.korovko.clinic.security.service.UserRegistrationService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRegistrationService userRegistrationService;

    @Value("${spring.redirect.registration}")
    private String registrationRedirectAddress;
    @Value("${spring.redirect.passwordRestoration}")
    private String passwordRestorationRedirectAddress;

    @PostMapping(value = "/register-start")
    public RegistrationResponse registerUser(@RequestBody @Valid RegistrationStartRequest request) {
        return userRegistrationService.registerStart(request);
    }

    @GetMapping("/register-finish")
    public RedirectView registerFinish(@RequestParam String confirmationCode) {
        userRegistrationService.registerFinish(confirmationCode);

        RedirectView redirectView = new RedirectView();
        redirectView.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        redirectView.setUrl(registrationRedirectAddress);

        return redirectView;
    }

    @PostMapping("/restore-start")
    public RegistrationResponse restoreStart(@RequestBody @Valid RestoreStartRequest request) {
        return userRegistrationService.restoreStart(request);
    }

    @PostMapping("/restore-finish")
    public RedirectView restoreFinish(@RequestParam String confirmationCode) {
        userRegistrationService.restoreFinish(confirmationCode);

        RedirectView redirectView = new RedirectView();
        redirectView.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        redirectView.setUrl(passwordRestorationRedirectAddress);
        return redirectView;
    }

    @PostMapping(value = "/login")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return new AuthenticationResponse()
                .setStatus(AuthenticationResponse.AuthenticationStatus.SUCCESS)
                .setToken(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN'")
    public CurrentUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return new CurrentUserDto()
                .setLogin(principal.getUsername())
                .setAuthorities(principal.getAuthorities().stream()
                        .map(o -> new SimpleGrantedAuthority(o.getAuthority()))
                        .collect(Collectors.toSet()));
    }
}
