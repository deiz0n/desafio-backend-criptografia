package com.deiz0n.cryptography.domain.exceptions;

import com.deiz0n.cryptography.domain.dtos.ResponseError;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(400).body(ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .title("Error creating user")
                .detail(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage())
                .instant(Instant.now())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException)
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);

        if (rootCause instanceof UnrecognizedPropertyException)
            return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status, request);

        return ResponseEntity.status(400).body(ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .title("Error creating user")
                .detail(ex.getCause().toString())
                .instant(Instant.now())
                .build());
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String fieldName = ex.getPath()
                .stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        var detail = String.format(
                "The property '%s' received a value: '%s'. But this property accepts only value types: '%s'",
                fieldName, ex.getValue(), ex.getTargetType().getSimpleName()
        );

        return ResponseEntity.status(400).body(ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .title("Invalid property")
                .detail(detail)
                .instant(Instant.now())
                .build());
    }

    private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String fieldName = ex.getPath()
                .stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        var detail = String.format(
                "The property '%s' does not exist",
                fieldName
        );

        return ResponseEntity.status(400).body(ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .title("Invalid property")
                .detail(detail)
                .instant(Instant.now())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(400).body(ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .title("Media type invalid")
                .detail("Only JSON format is accept")
                .instant(Instant.now())
                .build());
    }
}
