package com.ioBuilders.bank.domain.transaction.model;

/**
 * @author i.fernandez@nchain.com
 *
 * An Exception triggered by operation performed on Transactions
 */
public class TransactionException extends RuntimeException {
    public static final String NOT_ENOUGH_BALANCE = "NOT ENOUGH BALANCE";
    public static final String BOTH_EXTERNAL_ACCOUNTS = "BOTH_EXTERNAL_ACCOUNTS";
    public static final String TRANSACTION_TO_SAME_ACCOUNT = "TRANSACTION_TO_SAME_ACCOUNT";
    public TransactionException(String msg) {
        super(msg);
    }
}
