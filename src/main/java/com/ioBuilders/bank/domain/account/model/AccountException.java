package com.ioBuilders.bank.domain.account.model;

/**
 * @author i.fernandez@nchain.com
 *
 * An Exception triggered by an Operation performed on an Account.
 */
public class AccountException extends RuntimeException {
    public static final String NO_USER = "USER REFERENCED IS NOT REGISTERED";
    public static final String ACCOUNT_ALREADY_CREATED = "ACCOUNT ALREADY CREATED";
    public AccountException(String msg) {
        super(msg);
    }
}
