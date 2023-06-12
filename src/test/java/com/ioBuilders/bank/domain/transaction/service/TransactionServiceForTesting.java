package com.ioBuilders.bank.domain.transaction.service;

import com.ioBuilders.bank.domain.transaction.service.TransactionServiceImpl;
import com.ioBuilders.bank.infrastructure.account.storage.AccountStoreForTesting;
import com.ioBuilders.bank.infrastructure.transaction.storage.TransactionStoreForTesting;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class TransactionServiceForTesting extends TransactionServiceImpl {
    @Autowired
    public TransactionServiceForTesting(AccountStoreForTesting accountStoreForTesting,
                                        TransactionStoreForTesting transactionStoreForTesting) {
        super(accountStoreForTesting, transactionStoreForTesting);
    }

    public void clear() {
        ((TransactionStoreForTesting) super.transactionStorage).clear();
    }

}
