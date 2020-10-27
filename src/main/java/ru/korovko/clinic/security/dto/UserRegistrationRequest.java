package ru.korovko.clinic.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено") // TODO change
    private String userName;

    @NotBlank(message = "Пароль должен быть заполнен") //TODO change
    @Size(min = 8, message = "Длина пароля должна быть больше 8 символов")
    private String password;
}
