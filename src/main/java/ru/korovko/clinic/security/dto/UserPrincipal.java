package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class UserPrincipal implements UserDetails {

    public static final String SUBJECT = "Me";
    public static final String USER_EMAIL = "userEmail";
    public static final String ROLES = "roles";
    public static final String ACCOUNT_EXPIRED = "accountExpired";
    public static final String ACCOUNT_LOCKED = "accountLocked";
    public static final String CREDENTIALS_EXPIRED = "credentialsExpired";
    public static final String ENABLED = "enabled";
    public static final String USER_ID = "userId";
    private static final String ROLE_PREFIX = "ROLE_";

    private UUID userId;
    private String userEmail;
    private String password;
    private Set<SimpleGrantedAuthority> authorities;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean enabled;

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(this::buildSimpleGrantedAuthority)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return !enabled;
    }

    private SimpleGrantedAuthority buildSimpleGrantedAuthority(SimpleGrantedAuthority authority) {
        String role = authority.getAuthority();
        return role.startsWith(ROLE_PREFIX) ? authority : new SimpleGrantedAuthority(ROLE_PREFIX + role);
    }
}
