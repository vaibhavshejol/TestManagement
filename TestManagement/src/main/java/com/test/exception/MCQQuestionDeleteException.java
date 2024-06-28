package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MCQQuestionDeleteException extends RuntimeException {
    public MCQQuestionDeleteException(String message) {
        super(message);
    }
}
