package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.test.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends Exception{
    
    @ExceptionHandler
    public ResponseEntity<Object> categoryDuplicateException(DataDuplicateException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> categoryNotFoundException(DataNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> categoryDeleteException(DataDeleteException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex){
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<Object> invalidFileException(InvalidFileException ex) {
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex){
        log.error("Unexpected error occurred: " + ex.getMessage(), ex);
        ErrorResponse errorResponse=new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
