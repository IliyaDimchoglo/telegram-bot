package com.fulcrum.telegrambot.excpetion;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {
    public RestException(String message) {
        super(message);
    }
}