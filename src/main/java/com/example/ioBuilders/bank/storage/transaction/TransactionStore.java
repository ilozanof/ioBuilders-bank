package com.example.ioBuilders.bank.storage.transaction;

import com.example.ioBuilders.bank.domain.transaction.Transaction;
import com.example.ioBuilders.bank.domain.transaction.TransactionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class TransactionStore implements TransactionStorage {
    private final int PAGE_SIZE = 10;
    protected TransactionJpaStore transactionJpaStore;

    @Autowired
    public TransactionStore(TransactionJpaStore transactionJpaStore) {
        this.transactionJpaStore = transactionJpaStore;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        this.transactionJpaStore.save(new TransactionEntity(transaction));
    }


    @Override
    public List<Transaction> getTransactionsByAccount(String accountId, int page) {
        return this.transactionJpaStore
                .findTransactionEntitiesByAccount(accountId, PageRequest.of(page, PAGE_SIZE))
                .stream().map(TransactionEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    // TODO: THIS ONLY FOR TESTING!!!!
    public void clear() {
        this.transactionJpaStore.deleteAll();
    }
}
