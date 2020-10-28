package ru.korovko.clinic.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.korovko.clinic.entity.Speciality;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    private String firstName;
    private String lastName;
    private Speciality speciality;
    private String email; // TODO validation for password and email
    private String password;
}
