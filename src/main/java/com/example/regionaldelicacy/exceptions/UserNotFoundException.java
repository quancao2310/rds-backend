package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomDefineException {
    public UserNotFoundException() {
        super("Can not find any user with the provided email!", HttpStatus.NOT_FOUND);
    }
}
