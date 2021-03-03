package ru.korovko.clinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.security.dto.RegistrationStartRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "speciality", source = "specialty")
    User toUser(RegistrationStartRequest request);

    void toUser(@MappingTarget User user, RegistrationStartRequest request);
}
