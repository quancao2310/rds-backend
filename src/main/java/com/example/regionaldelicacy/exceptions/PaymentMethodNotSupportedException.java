package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class PaymentMethodNotSupportedException extends CustomException {
    public PaymentMethodNotSupportedException() {
        super("This payment method is currently not supported", HttpStatus.NOT_IMPLEMENTED);
    }
}
