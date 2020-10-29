package ru.korovko.clinic.annotation;

import ru.korovko.clinic.annotation.impl.EmailValidationImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EmailValidationImpl.class)
public @interface EmailValidation {

    String message() default "Email is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
