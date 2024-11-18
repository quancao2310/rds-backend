package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidJwtException extends CustomException {
    public InvalidJwtException() {
        super("Auth token is invalid", HttpStatus.UNAUTHORIZED);
    }
}
