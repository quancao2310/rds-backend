package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException() {
        super("Email has already been used!", HttpStatus.CONFLICT);
    }
}
