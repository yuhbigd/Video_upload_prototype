package com.example.vodproducer.exception;

import com.example.vodproducer.utils.MessagesUtils;

public class BusinessException extends RuntimeException {
    private String message;

    public BusinessException(String errorCode, Object... var2) {
        this.message = MessagesUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
