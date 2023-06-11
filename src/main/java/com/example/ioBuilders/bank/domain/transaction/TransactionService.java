package com.example.ioBuilders.bank.domain.transaction;


import java.util.Iterator;
import java.util.List;

public interface TransactionService {
    void executeTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccount(String accountId, int page);
}
