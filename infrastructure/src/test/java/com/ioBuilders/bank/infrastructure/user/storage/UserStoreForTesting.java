package com.ioBuilders.bank.infrastructure.user.storage;

import com.ioBuilders.bank.infrastructure.user.storage.UserJpaStore;
import com.ioBuilders.bank.infrastructure.user.storage.UserStore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class UserStoreForTesting extends UserStore {
    @Autowired
    public UserStoreForTesting(UserJpaStore userJpaStore) {
        super(userJpaStore);
    }

    public void clear() {
        this.userJpaStore.deleteAll();
    }
}
