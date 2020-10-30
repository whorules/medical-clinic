package ru.korovko.clinic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "registered_user")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "pg-uuid")
    private UUID id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;
    private String email;
    private String password;
    private Integer activationCode;
    private Boolean isActivated;
}