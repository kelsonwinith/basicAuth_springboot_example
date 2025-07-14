package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ApiResponse<T> {

    private String status;
    private int code;
    private String message;
    private T error;
    private T data;
    private Instant timestamp;

    // Success without data
    public static <T> ResponseEntity<ApiResponse<T>> success(String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status("success")
                .code(HttpStatus.OK.value())
                .message(message)
                .data(null)
                .error(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.ok(response);
    }

    // Success with data
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status("success")
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .error(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.ok(response);
    }

    // Created with data
    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status("created")
                .code(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .error(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Error with error data
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, T error) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .status("error")
                .code(status.value())
                .message(message)
                .error(error)
                .data(null)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
