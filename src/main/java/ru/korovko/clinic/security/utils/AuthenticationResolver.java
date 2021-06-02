package ru.korovko.clinic.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.korovko.clinic.security.dto.CurrentUserDto;
import ru.korovko.clinic.security.dto.UserPrincipal;
import ru.korovko.clinic.security.mapper.UserPrincipalMapper;

@Component
@RequiredArgsConstructor
public class AuthenticationResolver {

    private final UserPrincipalMapper mapper;

    public CurrentUserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return mapper.toCurrentUser((UserPrincipal) authentication.getPrincipal());
    }
}
