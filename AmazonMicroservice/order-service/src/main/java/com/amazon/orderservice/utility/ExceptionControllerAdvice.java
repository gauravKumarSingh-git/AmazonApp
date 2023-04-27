package com.amazon.orderservice.utility;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amazon.orderservice.exception.OrderException;
import com.amazon.orderservice.external.decoder.ErrorInfo;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorInfo> orderExceptionHandler(OrderException orderException){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(orderException.getMessage());
        errorInfo.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }
}
