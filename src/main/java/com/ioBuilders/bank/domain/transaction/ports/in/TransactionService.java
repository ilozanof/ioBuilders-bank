package com.ioBuilders.bank.domain.transaction.ports.in;


import com.ioBuilders.bank.domain.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {
    void executeTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccount(String accountId, int page);
}
