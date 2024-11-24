package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPaginationParametersException extends CustomException {
    public InvalidPaginationParametersException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
