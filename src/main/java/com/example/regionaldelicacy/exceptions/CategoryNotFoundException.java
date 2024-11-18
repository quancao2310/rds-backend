package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends CustomException {
    public CategoryNotFoundException() {
        super("Category not found", HttpStatus.NOT_FOUND);
    }
}
