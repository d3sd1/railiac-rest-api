package com.railiac.rest.rest.advisor;

import com.railiac.rest.rest.exception.RailiacException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RailiacException.class)
    public ResponseEntity<Object> UserException(
            RailiacException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}