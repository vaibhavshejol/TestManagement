package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MCQQuestionNotFoundException extends RuntimeException {
    public MCQQuestionNotFoundException(String message) {
        super(message);
    }
}
