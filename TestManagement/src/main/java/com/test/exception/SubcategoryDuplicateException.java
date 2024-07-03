package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubcategoryDuplicateException extends RuntimeException {

    public SubcategoryDuplicateException(String message) {
        super(message);
    }
}
