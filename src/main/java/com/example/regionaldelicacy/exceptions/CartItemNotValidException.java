package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class CartItemNotValidException extends CustomDefineException {
    public CartItemNotValidException() {
        super("Can not find the valid cart item(s) with provided ID or user's info", HttpStatus.NOT_FOUND);
    }
}
