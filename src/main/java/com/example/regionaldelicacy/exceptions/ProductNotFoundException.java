package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomDefineException {
    public ProductNotFoundException() {
        super("Product not found", HttpStatus.NOT_FOUND);
    }
}
