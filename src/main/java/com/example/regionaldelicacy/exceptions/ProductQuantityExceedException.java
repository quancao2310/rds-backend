package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class ProductQuantityExceedException extends CustomDefineException {
    public ProductQuantityExceedException() {
        super("The input quantity exceeds the number in stock", HttpStatus.BAD_REQUEST);
    }
}
