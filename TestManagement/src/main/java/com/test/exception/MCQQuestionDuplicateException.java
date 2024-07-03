package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MCQQuestionDuplicateException extends RuntimeException {

    public MCQQuestionDuplicateException(String message) {
        super(message);
    }
}