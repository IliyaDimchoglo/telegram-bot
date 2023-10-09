package com.fulcrum.telegrambot.excpetion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionControllerHandler {

    @ExceptionHandler(RestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto converterExceptionHandler(final RestException exception) {
        log.error(exception.getMessage() + "\n" + exception.getLocalizedMessage());
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessageDto exceptionHandler(final Exception exception) {
        log.error(exception.getMessage() + "\n" + exception.getLocalizedMessage());
        return new ExceptionMessageDto(exception.getMessage());
    }
}