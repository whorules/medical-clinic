package ru.korovko.clinic.service;

public interface MailService {

    void sendMessage(String address, String subject, String text);
}
