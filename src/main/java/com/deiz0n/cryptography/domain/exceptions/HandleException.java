package com.deiz0n.cryptography.domain.exceptions;

import com.deiz0n.cryptography.domain.dtos.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseError> handleUserNotFoundException() {
        return ResponseEntity.status(404).body(ResponseError.builder()
                .status(HttpStatus.NOT_FOUND)
                .code(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail("User not found")
                .instant(Instant.now())
                .build());
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<ResponseError> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return ResponseEntity.status(400).body(ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .title("Resource already registered")
                .detail(ex.getMessage())
                .instant(Instant.now())
                .build());
    }


}
