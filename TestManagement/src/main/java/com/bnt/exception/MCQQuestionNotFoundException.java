package com.bnt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Custom exception class for mcq question not found
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MCQQuestionNotFoundException extends RuntimeException {
    public MCQQuestionNotFoundException(String message) {
        super(message);
    }
}
