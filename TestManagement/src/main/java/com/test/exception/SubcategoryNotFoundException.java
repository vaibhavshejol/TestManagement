package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubcategoryNotFoundException extends RuntimeException {
    public SubcategoryNotFoundException(String message) {
        super(message);
    }
}
