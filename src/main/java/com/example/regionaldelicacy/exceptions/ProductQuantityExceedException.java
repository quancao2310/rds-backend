package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class ProductQuantityExceedException extends CustomDefineException {
    public ProductQuantityExceedException() {
        super("The required quantity for the product exceeds the number in stock", HttpStatus.BAD_REQUEST);
    }
}
