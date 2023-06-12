package com.ioBuilders.bank.infrastructure.account.storage;

import com.ioBuilders.bank.infrastructure.user.storage.UserJpaStore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class AccountStoreForTesting extends AccountStore {
    @Autowired
    public AccountStoreForTesting(AccountJpaStore accountJpaStore, UserJpaStore userJpaStore) {
        super(accountJpaStore, userJpaStore);
    }

    public void clear() {
        super.accountJpaStore.deleteAll();
    }
}
