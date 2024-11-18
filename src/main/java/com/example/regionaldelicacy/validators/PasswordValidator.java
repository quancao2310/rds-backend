package com.example.regionaldelicacy.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&~*+=%<>.,?;:'\"`_()\\[\\]{}|/\\\\-]).{8,}$"
    );
    
    // private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (!matcher.matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "Password must be at least 8 characters long, contain at least one lowercase letter, " +
                "one uppercase letter, one digit, and one special character")
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
