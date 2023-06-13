package com.ioBuilders.bank.domain.transaction.ports.out;

import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;

import java.util.List;
import java.util.Optional;

/**
 * A PORT that provides Storage capabilities for Transactions
 */
public interface TransactionStorage {
    int createTransaction(TransactionRequest transaction);
    Optional<Transaction> getTransaction(int transactionId);
    List<Transaction> getTransactionsByAccount(String accountId, int page);
}
