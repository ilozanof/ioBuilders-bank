package com.ioBuilders.bank.domain.user.model;

/**
 * @author i.fernandez@nchain.com
 *
 * An Exception triggered by an Operation performed on a User.
 */
public class UserException extends RuntimeException {
    public static final String ERR_ALREADY_REGISTERED = "User already Registered";
    public UserException(String msg) {
        super(msg);
    }
}
