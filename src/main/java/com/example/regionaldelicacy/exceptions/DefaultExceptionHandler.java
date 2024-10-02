package com.example.regionaldelicacy.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@SuppressWarnings("WeakerAccess")
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                        MethodArgumentNotValidException ex,
                        org.springframework.http.HttpHeaders headers, // Something weird happen here, so I have to write full class' package name
                        HttpStatusCode status,
                        WebRequest request) {
                ErrorMessageForException message = new ErrorMessageForException(
                                HttpStatus.BAD_REQUEST.value(),
                                new Date(),
                                ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
                                request.getDescription(false));

                return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorMessageForException> badCredentialsExceptionHandler(BadCredentialsException ex,
                        WebRequest request) {
                ErrorMessageForException message = new ErrorMessageForException(
                                HttpStatus.UNAUTHORIZED.value(),
                                new Date(),
                                "Email or password is incorrect!",
                                request.getDescription(false));

                return new ResponseEntity<ErrorMessageForException>(message, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorMessageForException> badCredentialsExceptionHandler(AccessDeniedException ex,
                        WebRequest request) {
                ErrorMessageForException message = new ErrorMessageForException(
                                HttpStatus.FORBIDDEN.value(),
                                new Date(),
                                "You are not authorized to access this resource!",
                                request.getDescription(false));

                return new ResponseEntity<ErrorMessageForException>(message, HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(CustomDefineException.class)
        public ResponseEntity<ErrorMessageForException> customDefineExceptionHandler(CustomDefineException ex,
                        WebRequest request) {
                ErrorMessageForException message = new ErrorMessageForException(
                                ex.getHttpStatusCode().value(),
                                new Date(),
                                ex.getMessage(),
                                request.getDescription(false));

                return new ResponseEntity<ErrorMessageForException>(message, ex.getHttpStatusCode());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorMessageForException> globalExceptionHandler(Exception ex, WebRequest request) {
                ErrorMessageForException message = new ErrorMessageForException(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                new Date(),
                                ex.getMessage(),
                                request.getDescription(false));

                return new ResponseEntity<ErrorMessageForException>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
