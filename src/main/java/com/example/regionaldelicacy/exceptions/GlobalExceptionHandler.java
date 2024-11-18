package com.example.regionaldelicacy.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("Validation error occurred at {}: {}", ex.getClass().getName(), ex.getMessage(), ex);
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            errors.toString(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, 
            Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("Internal exception occurred: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
            status.value(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
            BadCredentialsException ex,
            WebRequest request) {
        log.error("{} with status 401: {}", ex.getClass().getName(), ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Email or password is incorrect!",
            request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> badCredentialsExceptionHandler(
            AccessDeniedException ex,
            WebRequest request) {
        log.error("{} with status 403: {}", ex.getClass().getName(), ex.getMessage());

        ErrorResponse message = new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            "You are not authorized to access this resource!",
            request.getDescription(false)
        );
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(
            CustomException ex,
            WebRequest request) {
        log.error("{} with status {}: {}", ex.getClass().getSimpleName(), ex.getStatus().value(), ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
            ex.getStatus().value(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error("Exception {}: {}", ex.getClass().getName(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
