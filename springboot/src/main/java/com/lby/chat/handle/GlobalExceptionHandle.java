package com.lby.chat.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author: laishao
 * Date: 2022/5/11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = {Exception.class,RuntimeException.class})
    public ResponseEntity<String> ExceptionHandle(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}