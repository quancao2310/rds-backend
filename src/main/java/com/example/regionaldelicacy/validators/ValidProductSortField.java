package com.example.regionaldelicacy.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductSortFieldValidator.class)
public @interface ValidProductSortField {
    String message() default "Invalid sort field. Allowed values are: ${validValues}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
