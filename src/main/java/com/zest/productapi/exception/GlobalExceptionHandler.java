package com.zest.productapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("NOT_FOUND")
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build();

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {

    String message = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error ->
                    error.getField() + ": " + error.getDefaultMessage()
            )
            .collect(Collectors.joining(", "));

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("VALIDATION_ERROR")
            .message(message)
            .path(request.getRequestURI())
            .build();

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
}

@ExceptionHandler(ConstraintViolationException.class)
public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("VALIDATION_ERROR")
            .message(exception.getMessage())
            .path(request.getRequestURI())
            .build();

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {

    ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("INTERNAL_SERVER_ERROR")
            .message("An unexpected error occurred")
            .path(request.getRequestURI())
            .build();

    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
}


}
