package com.example.regionaldelicacy.exceptions;

import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends CustomException {
    public AddressNotFoundException() {
        super("Address not found", HttpStatus.NOT_FOUND);
    }
}
