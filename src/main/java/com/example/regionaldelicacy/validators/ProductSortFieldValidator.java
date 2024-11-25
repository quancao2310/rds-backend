package com.example.regionaldelicacy.validators;

import com.example.regionaldelicacy.constants.ProductSortingConstants;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductSortFieldValidator implements ConstraintValidator<ValidProductSortField, String> {

    @Override
    public boolean isValid(String sortField, ConstraintValidatorContext context) {
        if (sortField == null) {
            return true;
        }

        if (!ProductSortingConstants.ALLOWED_SORT_FIELDS.contains(sortField)) {
            String message = String.format("Invalid sort field: '%s'. Allowed values are: %s", 
                sortField, ProductSortingConstants.getAllowedSortFieldsString());
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
