package com.example.ioBuilders.bank.storage.transaction;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class TransactionStoreForTesting extends TransactionStore {
    @Autowired
    public TransactionStoreForTesting(TransactionJpaStore transactionJpaStore) {
        super(transactionJpaStore);
    }

    public void clear() {
        super.transactionJpaStore.deleteAll();
    }
}
