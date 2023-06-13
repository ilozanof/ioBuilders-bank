package com.ioBuilders.bank.domain.transaction.ports.in;


import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;

import java.util.List;
import java.util.Optional;

/**
 * Operations allowed on Transactions at bussiness-level
 */
public interface TransactionService {
    /**
     * Executes the Transactions and updates both Balances
     */
    int executeTransaction(TransactionRequest transaction);

    /**
     * Retrieves a specific Transaction
     */
    Optional<Transaction> getTransaction(int transactionId);

    /**
     * Returns the paginated List of all the Transactions belonging to an Account given
     */
    List<Transaction> getTransactionsByAccount(String accountId, int page);
}
