package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidJwtException extends CustomException {
    public InvalidJwtException() {
        super("Authentication credentials are invalid or not found", HttpStatus.UNAUTHORIZED);
    }
}
