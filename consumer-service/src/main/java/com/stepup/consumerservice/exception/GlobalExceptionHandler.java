package com.stepup.consumerservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling exceptions that occur during HTTP request processing.
 * This class is annotated with {@link lombok.extern.slf4j.Slf4j} to enable logging using SLF4J
 * and {@link org.springframework.web.bind.annotation.RestControllerAdvice} to provide centralized
 * exception handling across all controllers.
 *
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.web.bind.annotation.RestControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.http.ResponseEntity
 * @see org.springframework.http.HttpStatus
 * @see org.springframework.http.converter.HttpMessageNotReadableException
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link org.springframework.http.converter.HttpMessageNotReadableException} by logging
     * the error and returning a meaningful error response to the client.
     *
     * @param ex The HttpMessageNotReadableException that occurred.
     * @return ResponseEntity containing an error message indicating that an error occurred while processing the request.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Log the error
        log.error("Error while reading HTTP message: {}", ex.getMessage());

        // Return a meaningful error response to the client
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing the request.");
    }
}
