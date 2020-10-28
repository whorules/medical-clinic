package ru.korovko.clinic.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.korovko.clinic.entity.User;
import ru.korovko.clinic.security.dto.UserPrincipal;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserPrincipalMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "authorities", source = "entity", qualifiedByName = "authoritiesMapping")
    UserPrincipal entityToDto(User entity);

    @Named("authoritiesMapping")
    default Set<SimpleGrantedAuthority> authoritiesMapping(User user) {
        return Set.of(new SimpleGrantedAuthority(user.getSpeciality().name()));
    }
}
