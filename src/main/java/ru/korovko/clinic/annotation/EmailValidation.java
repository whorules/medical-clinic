package ru.korovko.clinic.annotation;

import ru.korovko.clinic.annotation.impl.PasswordValidationImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordValidationImpl.class)
public @interface EmailValidation {

    String message() default "Email is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
