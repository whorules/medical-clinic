package ru.korovko.clinic.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AuthenticationResponse implements Serializable {

    private String token;
}
