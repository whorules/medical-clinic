package ru.korovko.clinic.security.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.korovko.clinic.security.dto.UserPrincipal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header) || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        UserPrincipal principal = tokenProvider.getPrincipalFromToken(header.substring(TOKEN_PREFIX.length()));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
