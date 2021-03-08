package ru.korovko.clinic.service;

import ru.korovko.clinic.security.dto.UserAction;

public interface MailService {

    void sendMessage(String address, String subject, String text);

    String generateTextForMessage(String confirmCode, UserAction action);
}
