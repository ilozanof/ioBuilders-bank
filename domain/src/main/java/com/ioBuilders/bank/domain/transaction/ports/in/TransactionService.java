package com.ioBuilders.bank.domain.transaction.ports.in;


import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    int executeTransaction(TransactionRequest transaction);
    Optional<Transaction> getTransaction(int transactionId);
    List<Transaction> getTransactionsByAccount(String accountId, int page);
}
