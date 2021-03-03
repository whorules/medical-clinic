package ru.korovko.clinic.validation.impl;

import org.apache.commons.validator.routines.EmailValidator;
import ru.korovko.clinic.validation.EmailValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidationImpl implements ConstraintValidator<EmailValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EmailValidator.getInstance().isValid(value);
    }
}
