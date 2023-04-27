package com.amazon.productservice.utility;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amazon.productservice.exception.ProductException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorInfo> productExceptionHandler(ProductException productException){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(productException.getMessage());
        errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }
}
