package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.test.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception{
    
    @ExceptionHandler
    public ResponseEntity<Object> categoryDuplicateException(CategoryDuplicateException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> categoryNotFoundException(CategoryNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> subcategoryDuplicateException(SubcategoryDuplicateException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Object> subcategoryNotFoundException(SubcategoryNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> mcqQuestionDuplicateException(MCQQuestionDuplicateException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<Object> mcqQuestionNotFoundException(MCQQuestionNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
