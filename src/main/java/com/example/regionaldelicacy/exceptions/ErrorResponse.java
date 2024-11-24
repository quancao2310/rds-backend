package com.example.regionaldelicacy.exceptions;

import java.time.Instant;

import lombok.Data;

@Data
public class ErrorResponse {
    private Instant timestamp;
    private int statusCode;
    private String message;
    private String path;

    public ErrorResponse(int statusCode, String message, String path) {
        this.timestamp = Instant.now();
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
    }
}
