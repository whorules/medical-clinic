package ru.korovko.clinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.korovko.clinic.dto.CreatePatientRequest;
import ru.korovko.clinic.entity.Patient;
import ru.korovko.clinic.entity.User;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.firstName", target = "firstName")
    @Mapping(source = "request.firstName", target = "lastName")
    @Mapping(source = "doctor", target = "doctor")
    Patient toPatient(CreatePatientRequest request, User doctor);
}
