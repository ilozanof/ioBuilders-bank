package com.ioBuilders.bank.infrastructure.rest;

import com.ioBuilders.bank.domain.account.model.AccountException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.ArrayList;
import java.util.List;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */

@ControllerAdvice
public class CustomRestExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleException(Exception ex) {
//        List<String> errors = new ArrayList<>();
////        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
////            errors.add(error.getField() + ": " + error.getDefaultMessage());
////        }
////        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
////            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
////        }
//
//        return new ResponseEntity(new RestApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<RestApiError> handleMethodArgumentNotValid(MethodArgumentTypeMismatchException ex) {
        RestApiError errorApi = new RestApiError(
                HttpStatus.BAD_REQUEST,
                "Value of Parameters incorrect",
                "Parameter '" + ex.getPropertyName() + "': " + ex.getMessage());
        return new ResponseEntity(errorApi, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<RestApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        RestApiError errorApi = new RestApiError(
                ex.getHttpStatus(),
                "Resource Not Found",
                ex.getMessage() + "Not Found");
        return new ResponseEntity(errorApi, ex.getHttpStatus());
    }

    @ExceptionHandler({AccountException.class})
    protected ResponseEntity<RestApiError> handleAccountException(AccountException ex) {
        RestApiError errorApi = new RestApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                ex.getDetail());
        return new ResponseEntity(errorApi, HttpStatus.BAD_REQUEST);
    }
}
