package ru.korovko.clinic.security.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korovko.clinic.security.mapper.UserPrincipalMapper;
import ru.korovko.clinic.security.repository.UserRepository;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserPrincipalMapper mapper;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByEmail(name)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user by name " + name));
    }
}
