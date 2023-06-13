package com.ioBuilders.bank.infrastructure.storage.transaction;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.fernandez@nchain.com
 *
 * This Store adds other meethods useful for testing but too "dangerous" to be included in the "main" codebase.
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
