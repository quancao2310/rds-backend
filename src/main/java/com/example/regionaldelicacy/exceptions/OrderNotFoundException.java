package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends CustomDefineException {
    public OrderNotFoundException() {
        super("Can not find the order with provided ID or user's info", HttpStatus.NOT_FOUND);
    }
}
