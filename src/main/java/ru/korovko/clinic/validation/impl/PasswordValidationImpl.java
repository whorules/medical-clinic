package ru.korovko.clinic.validation.impl;

import org.springframework.util.StringUtils;
import ru.korovko.clinic.validation.PasswordValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidationImpl implements ConstraintValidator<PasswordValidation, String> {

    private static final String PASSWORD_REGEXP = "\\A[\\x21-\\x7FёЁ\\u0410-\\u044F]*\\z";
    private static final short PASSWORD_MIN_LENGTH = 8;
    private static final short PASSWORD_MAX_LENGTH = 127;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!isPasswordLengthValid(value) || StringUtils.containsWhitespace(value)) {
            return false;
        }

        if (!Pattern.matches(PASSWORD_REGEXP, value)) {
            return false;
        }
        return doesContainAtLeastOneCharacter(value);
    }

    private boolean isPasswordLengthValid(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH;
    }
    
    private boolean doesContainAtLeastOneCharacter(String password) {
        boolean upperCaseLetter = false;
        boolean lowerCaseLetter = false;
        boolean digit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digit = true;
            }
            if (Character.isUpperCase(c)) {
                upperCaseLetter = true;
            }
            if (Character.isLowerCase(c)) {
                lowerCaseLetter = true;
            }
        }
        return upperCaseLetter && lowerCaseLetter && digit;
    }
}