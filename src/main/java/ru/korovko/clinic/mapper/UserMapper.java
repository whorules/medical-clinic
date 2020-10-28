package ru.korovko.clinic.mapper;

import org.mapstruct.Mapper;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.security.dto.UserRegistrationRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRegistrationRequest request);
}
