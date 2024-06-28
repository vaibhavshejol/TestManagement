package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryDeleteException extends RuntimeException {

    public CategoryDeleteException(String message) {
        super(message);
    }
}