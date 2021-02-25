package ru.korovko.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korovko.clinic.entity.Session;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.repository.SessionRepository;
import ru.korovko.clinic.security.repository.UserRepository;
import ru.korovko.clinic.service.SessionService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public String create(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user with such email: " + email));
        sessionRepository.findByUserId(user.getId()).ifPresent(sessionRepository::delete);

        Session session = new Session();
        session.setUser(user);
        session.setExpiresAt(LocalDateTime.now().plusMinutes(30));

        Session saved = sessionRepository.save(session);
        return saved.getId().toString();
    }

    @Override
    @Transactional(readOnly = true)
    public Session getById(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No session with such id: " + id));
    }
}
