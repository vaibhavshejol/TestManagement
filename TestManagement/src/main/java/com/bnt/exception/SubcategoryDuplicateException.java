package com.bnt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Custom exception class for duplicate entry of subcategory
@ResponseStatus(HttpStatus.CONFLICT)
public class SubcategoryDuplicateException extends RuntimeException {

    public SubcategoryDuplicateException(String message) {
        super(message);
    }
}
