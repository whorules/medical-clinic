package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Data
@Accessors(chain = true)
public class CurrentUserDto {

    private String login;
    private Set<SimpleGrantedAuthority> authorities;
}
