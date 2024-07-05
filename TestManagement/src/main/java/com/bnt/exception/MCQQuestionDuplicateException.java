package com.bnt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Custom exception class for duplicate entry of mcq question
@ResponseStatus(HttpStatus.CONFLICT)
public class MCQQuestionDuplicateException extends RuntimeException {

    public MCQQuestionDuplicateException(String message) {
        super(message);
    }
}