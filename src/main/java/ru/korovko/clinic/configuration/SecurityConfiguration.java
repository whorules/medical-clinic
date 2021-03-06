package ru.korovko.clinic.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.korovko.clinic.security.service.impl.CustomExceptionTranslationFilter;
import ru.korovko.clinic.security.service.impl.CustomUserDetailsService;
import ru.korovko.clinic.security.service.impl.JwtAuthorizationFilter;
import ru.korovko.clinic.security.service.impl.TokenProvider;
import ru.korovko.clinic.security.service.impl.UserServiceAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final int BCRYPT_PASSWORD_STRENGTH = 8;

    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/auth/login/**",
                        "/auth/register-start",
                        "/auth/register-finish",
                        "/auth/restore-start",
                        "/auth/restore-confirm/**",
                        "/auth/restore-finish")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthorizationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomExceptionTranslationFilter(userAuthenticationEntryPoint()), JwtAuthorizationFilter.class)
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/actuator/**",
                "/csrf",
                "/error",
                "/favicon.ico",
                "/v2/api-docs**",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_PASSWORD_STRENGTH);
    }

    @Bean
    public AuthenticationEntryPoint userAuthenticationEntryPoint() {
        return new UserServiceAuthenticationEntryPoint(objectMapper);
    }
}