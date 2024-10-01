package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatusCode;

public class CustomDefineException extends RuntimeException {
    private HttpStatusCode httpStatusCode;

    public CustomDefineException(String ex, HttpStatusCode httpStatus) {
        super(ex);
        this.httpStatusCode = httpStatus;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }
}
