package com.abdulchakam.codinginterview.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        var errorResponse = new HashMap<String, List<String>>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleClientException(DataNotFoundException ex) {
        logger.error("DataNotFound Exception : ", ex);
        var body = new HashMap<String, String>();
        body.put("errors", ex.getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<Object> handleClientException(DataAlreadyExistException ex) {
        logger.error("DataAlreadyExist Exception : ", ex);
        var body = new HashMap<String, String>();
        body.put("errors", ex.getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }
}
