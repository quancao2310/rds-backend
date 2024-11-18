package com.example.regionaldelicacy.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private ZonedDateTime timestamp;
    private int statusCode;
    private String message;
    private String path;

    public ErrorResponse(int statusCode, String message, String path) {
        this.timestamp = ZonedDateTime.now(ZoneId.of("UTC"));
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
    }
}
