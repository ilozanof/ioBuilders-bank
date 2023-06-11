package com.example.ioBuilders.bank.domain.transaction;

import com.example.ioBuilders.bank.domain.account.AccountServiceForTesting;
import com.example.ioBuilders.bank.storage.account.AccountStoreForTesting;
import com.example.ioBuilders.bank.storage.transaction.TransactionStoreForTesting;
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
