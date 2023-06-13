package com.ioBuilders.bank.domain.account.model;

/**
 * @author i.fernandez@nchain.com
 *
 * An Exception triggered by an Operation performed on an Account.
 */
public class AccountException extends RuntimeException {
    public static final String NO_USER = "USER REFERENCED IS NOT REGISTERED";
    public static final String ACCOUNT_ALREADY_CREATED = "ACCOUNT ALREADY CREATED";
    public static final String ACCOUNT_INVALID = "ACCOUNT_INVALID";
    public static final String ACCOUNT_BALANCE_NEGATIVE = "NEGATIVE BALANCE";

    private String detail;

    public AccountException(String msg) {
        super(msg);
    }
    public AccountException(String msg, String detail) {
        this(msg);
        this.detail = detail;
    }

    public String getDetail() { return this.detail;}
}
