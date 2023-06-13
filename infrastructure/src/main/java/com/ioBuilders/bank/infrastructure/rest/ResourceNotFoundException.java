package com.ioBuilders.bank.infrastructure.rest;

import org.springframework.http.HttpStatus;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class ResourceNotFoundException extends RuntimeException {
    private HttpStatus httpStatus;
    public ResourceNotFoundException(HttpStatus httpStatus, String resourceName) {
        super(resourceName);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() { return this.httpStatus;}
}
