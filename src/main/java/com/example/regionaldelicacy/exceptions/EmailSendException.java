package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class EmailSendException extends CustomException {
    public EmailSendException() {
        super("Failed to send email", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
