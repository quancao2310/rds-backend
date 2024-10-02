package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomDefineException {
    public InvalidTokenException() {
        super("Auth token is invalid", HttpStatus.UNAUTHORIZED);
    }
}
