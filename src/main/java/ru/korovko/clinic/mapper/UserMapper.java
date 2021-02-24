package ru.korovko.clinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "speciality", source = "specialty")
    User toUser(RegistrationStartRequest request);
}
