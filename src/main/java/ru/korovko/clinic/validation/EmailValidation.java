package ru.korovko.clinic.validation;

import ru.korovko.clinic.validation.impl.EmailValidationImpl;

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

    String message() default "Has incorrect format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
