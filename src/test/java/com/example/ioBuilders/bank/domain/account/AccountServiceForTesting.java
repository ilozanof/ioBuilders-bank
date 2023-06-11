package com.example.ioBuilders.bank.domain.account;

import com.example.ioBuilders.bank.storage.account.AccountJpaStore;
import com.example.ioBuilders.bank.storage.account.AccountStore;
import com.example.ioBuilders.bank.storage.account.AccountStoreForTesting;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class AccountServiceForTesting extends AccountServiceImpl {
    public AccountServiceForTesting(AccountStoreForTesting accountStoreForTesting) {
        super(accountStoreForTesting);
    }

    public void clear() {
        ((AccountStoreForTesting) super.accountStorage).clear();
    }
}
