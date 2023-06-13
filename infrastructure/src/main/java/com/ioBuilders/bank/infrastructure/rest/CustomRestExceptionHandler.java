package com.ioBuilders.bank.infrastructure.rest;

import com.ioBuilders.bank.domain.account.model.AccountException;
import com.ioBuilders.bank.domain.transaction.model.TransactionException;
import com.ioBuilders.bank.domain.user.model.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author i.fernandez@nchain.com
 *
 * Custom Exception Handler
 */

@ControllerAdvice
public class CustomRestExceptionHandler {

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

    @ExceptionHandler({UserException.class})
    protected ResponseEntity<RestApiError> handleUserException(UserException ex) {
        RestApiError errorApi = new RestApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "");
        return new ResponseEntity(errorApi, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransactionException.class})
    protected ResponseEntity<RestApiError> handleTransactionException(TransactionException ex) {
        RestApiError errorApi = new RestApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "");
        return new ResponseEntity(errorApi, HttpStatus.BAD_REQUEST);
    }
}
