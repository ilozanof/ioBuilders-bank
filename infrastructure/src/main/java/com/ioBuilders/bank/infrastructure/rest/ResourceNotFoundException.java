package com.ioBuilders.bank.infrastructure.rest;

import org.springframework.http.HttpStatus;

/**
 * @author i.fernandez@nchain.com
 *
 * Exception triggered when a Resource is not Found. Depending on the Use Case, this might be:
 * - NOT_FOUND: If the use case is a search
 * - BAD_REQUEST: If the operations relies on some darta and any of them is NOT Found.
 */
public class ResourceNotFoundException extends RuntimeException {
    private HttpStatus httpStatus;
    public ResourceNotFoundException(HttpStatus httpStatus, String resourceName) {
        super(resourceName);
        this.httpStatus = httpStatus;
    }
    public HttpStatus getHttpStatus() { return this.httpStatus;}
}
