package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ApiResponse.error(HttpStatus.BAD_REQUEST, "MethodArgumentNotValidException", errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ApiResponse.error(HttpStatus.NOT_FOUND, "ResourceNotFoundException", ex.getMessage());
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEntryException(DuplicateEntryException ex) {
        return ApiResponse.error(HttpStatus.CONFLICT, "DuplicateEntryException", ex.getMessage());
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnexpectedException(UnexpectedException ex) {
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "UnexpectedException", ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidRequestException(InvalidRequestException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, "InvalidRequestException", ex.getMessage());
    }

}
