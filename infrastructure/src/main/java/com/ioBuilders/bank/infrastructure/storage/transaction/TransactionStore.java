package com.ioBuilders.bank.infrastructure.storage.transaction;

import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;
import com.ioBuilders.bank.domain.transaction.ports.out.TransactionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
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
    public int createTransaction(TransactionRequest transaction) {
        return this.transactionJpaStore.save(new TransactionEntity(transaction)).getId();
    }

    @Override
    public Optional<Transaction> getTransaction(int transactionId) {
        Optional<TransactionEntity> transactionEntity = this.transactionJpaStore.findById(transactionId);
        return (transactionEntity.isPresent())
                ? Optional.of(transactionEntity.get().toDomain())
                : Optional.empty();
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountId, int page) {
        return this.transactionJpaStore
                .findTransactionEntitiesByAccount(accountId, PageRequest.of(page, PAGE_SIZE))
                .stream().map(TransactionEntity::toDomain)
                .collect(Collectors.toList());
    }
}
