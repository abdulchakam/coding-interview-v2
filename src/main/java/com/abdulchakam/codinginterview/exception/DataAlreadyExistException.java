package com.abdulchakam.codinginterview.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataAlreadyExistException extends RuntimeException {
    private final HttpStatus httpStatus;

    public DataAlreadyExistException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
