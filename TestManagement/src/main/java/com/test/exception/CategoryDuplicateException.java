package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryDuplicateException extends RuntimeException {

    public CategoryDuplicateException(String message) {
        super(message);
    }
}
