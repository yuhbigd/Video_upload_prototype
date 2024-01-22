package com.example.vodproducer.exception;

public class InternalServerException extends BusinessException {

    public InternalServerException(String errorCode, Object... var2) {
        super(errorCode, var2);
    }

}