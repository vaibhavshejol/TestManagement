package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubcategoryDeleteException extends RuntimeException {
    public SubcategoryDeleteException(String message) {
        super(message);
    }
}
