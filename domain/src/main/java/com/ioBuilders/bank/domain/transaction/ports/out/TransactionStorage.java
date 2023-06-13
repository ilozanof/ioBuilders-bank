package com.ioBuilders.bank.domain.transaction.ports.out;

import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;

import java.util.List;
import java.util.Optional;

/**
 * A PORT that provides Storage capabilities for Transactions
 */
public interface TransactionStorage {

    /** It Stores a Tx */
    int createTransaction(TransactionRequest transaction);

    /** It retrieves a specific Tx */
    Optional<Transaction> getTransaction(int transactionId);

    /** It returns a Paginated list of the Tx belonging to an account given */
    List<Transaction> getTransactionsByAccount(String accountId, int page);
}
