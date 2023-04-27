package com.amazon.orderservice.external.decoder;

import java.io.IOException;

import com.amazon.orderservice.exception.OrderException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String arg0, Response arg1) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            ErrorInfo errorInfo = objectMapper.readValue(arg1.body().asInputStream(), ErrorInfo.class);
            return new OrderException(errorInfo.getErrorMessage());

        }catch (IOException e) {
            log.info(e.getMessage());
            return new OrderException(e.getMessage());
        }
    }
    
}
