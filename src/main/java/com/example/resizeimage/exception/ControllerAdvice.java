package com.example.resizeimage.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.info(e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e.getMessage());
        for (String message : Arrays.toString(e.getStackTrace()).split(",")) {
            stringBuilder.append(message).append("\n");
        }

        log.error(stringBuilder.toString());
        return ResponseEntity.internalServerError().build();
    }
}
