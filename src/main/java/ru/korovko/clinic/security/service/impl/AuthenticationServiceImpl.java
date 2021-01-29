package ru.korovko.clinic.security.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.korovko.clinic.security.dto.AuthenticationRequest;
import ru.korovko.clinic.security.dto.UserPrincipal;
import ru.korovko.clinic.security.service.AuthenticationService;

@Service
@AllArgsConstructor
class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Override
    public String authenticate(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getUserEmail();
        String password = authenticationRequest.getPassword();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return tokenProvider.buildToken(email, principal);
    }
}
