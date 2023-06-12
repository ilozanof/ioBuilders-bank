package com.ioBuilders.bank.domain.transaction.ports.out;

import com.ioBuilders.bank.domain.transaction.model.Transaction;

import java.util.List;

/**
 * A PORT that provides Storage capabilities for Transactions
 */
public interface TransactionStorage {
    void createTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccount(String accountId, int page);
}
