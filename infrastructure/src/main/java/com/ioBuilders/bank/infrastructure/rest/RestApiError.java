package com.ioBuilders.bank.infrastructure.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * @author i.fernandez@nchain.com
 *
 * A Wrapper to enrich the Error messages returned to the Client.
 */
@Data
@AllArgsConstructor
public class RestApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public RestApiError(HttpStatus status, String message, String error) {
        this(status, message, Arrays.asList(error));
    }

    public static RestApiError fromError(HttpStatus httpStatus, String message) {
        return new RestApiError(httpStatus, message, (String) null);
    }
}
