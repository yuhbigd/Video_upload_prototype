package com.example.vodproducer.exception;


public class BadRequestException extends BusinessException {

    public BadRequestException(String errorCode, Object... var2) {
        super(errorCode, var2);
    }
}
