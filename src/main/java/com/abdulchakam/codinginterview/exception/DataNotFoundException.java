package com.abdulchakam.codinginterview.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataNotFoundException extends RuntimeException{

    private final HttpStatus httpStatus;

    public DataNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
