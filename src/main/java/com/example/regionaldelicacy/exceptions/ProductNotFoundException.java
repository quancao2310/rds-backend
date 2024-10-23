package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomDefineException {
    public ProductNotFoundException() {
        super("Can not find the product", HttpStatus.NOT_FOUND);
    }
}
