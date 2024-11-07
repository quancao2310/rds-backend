package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class DiscountCodeNotValidException extends CustomDefineException {
    public DiscountCodeNotValidException() {
        super("Can not find the valid discount program with provided code", HttpStatus.NOT_FOUND);
    }
}
