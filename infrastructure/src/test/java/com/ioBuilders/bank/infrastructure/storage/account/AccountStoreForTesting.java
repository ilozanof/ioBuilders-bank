package com.ioBuilders.bank.infrastructure.storage.account;

import com.ioBuilders.bank.infrastructure.storage.account.AccountJpaStore;
import com.ioBuilders.bank.infrastructure.storage.account.AccountStore;
import com.ioBuilders.bank.infrastructure.storage.user.UserJpaStore;
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
