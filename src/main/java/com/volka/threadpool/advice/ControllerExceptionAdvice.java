package com.volka.threadpool.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 예외처리 컨트롤러 어드바이스
 *
 * @author volka
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler
    public String handle(Exception e) {
        return e.getMessage();
    }
}
