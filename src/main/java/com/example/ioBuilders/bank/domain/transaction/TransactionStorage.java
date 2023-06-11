package com.example.ioBuilders.bank.domain.transaction;

import java.util.Iterator;
import java.util.List;

/**
 * A PORT that provides Storage capabilities for Transactions
 */
public interface TransactionStorage {
    void createTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccount(String accountId, int page);
    void clear();
}
