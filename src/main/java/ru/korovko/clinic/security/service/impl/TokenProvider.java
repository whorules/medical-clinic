package ru.korovko.clinic.security.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.korovko.clinic.configuration.props.ApplicationSecurityProperties;
import ru.korovko.clinic.security.dto.LoggedUser;
import ru.korovko.clinic.security.dto.UserPrincipal;
import ru.korovko.clinic.security.exception.InvalidTokenDataException;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final ApplicationSecurityProperties applicationSecurityProperties;

    public UserPrincipal getPrincipalFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(applicationSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8)))
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            UserPrincipal userPrincipal = new UserPrincipal()
                    .setUserEmail((String) body.get(UserPrincipal.USER_EMAIL))
                    .setAuthorities(((List<String>) body.get(UserPrincipal.ROLES)).stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet()))
                    .setAccountExpired((boolean) body.get(UserPrincipal.ACCOUNT_EXPIRED))
                    .setAccountLocked((boolean) body.get(UserPrincipal.ACCOUNT_LOCKED))
                    .setCredentialsExpired((boolean) body.get(UserPrincipal.CREDENTIALS_EXPIRED))
                    .setEnabled((boolean) body.get(UserPrincipal.ENABLED));
            userPrincipal.setUserId(UUID.fromString((String) body.get(LoggedUser.USER_ID)));
            return userPrincipal;
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException("Token expired");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException("Invalid token");
        } catch (SignatureException e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException("Invalid token signature");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InvalidTokenDataException("Invalid token data");
        }
    }

    public String buildToken(String userEmail, UserPrincipal principal) {
        return Jwts.builder()
                .setSubject(userEmail)
                .signWith(Keys.hmacShaKeyFor(applicationSecurityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(applicationSecurityProperties.getJwtTimeToLive())))
                .claim(LoggedUser.USER_ID, principal.getUserId())
                .claim(UserPrincipal.ROLES, principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .claim(UserPrincipal.ACCOUNT_EXPIRED, principal.isAccountExpired())
                .claim(UserPrincipal.ACCOUNT_LOCKED, principal.isAccountLocked())
                .claim(UserPrincipal.CREDENTIALS_EXPIRED, principal.isCredentialsExpired())
                .claim(UserPrincipal.ENABLED, principal.isEnabled())
                .compact();
    }
}
