package com.ioBuilders.bank.domain.account.service;

import com.ioBuilders.bank.domain.account.service.AccountServiceImpl;
import com.ioBuilders.bank.infrastructure.account.storage.AccountStoreForTesting;

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
