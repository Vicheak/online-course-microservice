package com.vicheak.app.video.service.exception;

import com.vicheak.app.video.service.base.BaseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException ex) {
        return new ResponseEntity<>(BaseError.builder()
                .isSuccess(false)
                .message("Something went wrong, please check...!")
                .code(ex.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .errors(ex.getReason())
                .build(), ex.getStatusCode());
    }

}

