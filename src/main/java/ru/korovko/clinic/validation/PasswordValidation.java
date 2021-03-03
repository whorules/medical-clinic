package ru.korovko.clinic.validation;

import ru.korovko.clinic.validation.impl.PasswordValidationImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PasswordValidationImpl.class)
public @interface PasswordValidation {

    String message() default "Password must contain at least 8 symbols, included digits, upper and lower case letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}