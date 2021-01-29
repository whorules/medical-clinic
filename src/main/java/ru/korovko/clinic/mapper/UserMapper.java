package ru.korovko.clinic.mapper;

import org.mapstruct.Mapper;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegistrationStartRequest request);
}
