package com.example.vodproducer.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.vodproducer.exception.BadRequestException;
import com.example.vodproducer.exception.InternalServerException;
import com.example.vodproducer.viewmodel.error.ErrorVm;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorVm> handleBadRequestException(BadRequestException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorVm errorVm = new ErrorVm(HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message, null);
        return ResponseEntity.badRequest().body(errorVm);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorVm> handleInternalException(InternalServerException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorVm errorVm = new ErrorVm(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                message, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVm);
    }

}