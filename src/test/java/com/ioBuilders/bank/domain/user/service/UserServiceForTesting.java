package com.ioBuilders.bank.domain.user.service;

import com.ioBuilders.bank.domain.user.service.UserServiceImpl;
import com.ioBuilders.bank.infrastructure.user.storage.UserStoreForTesting;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class UserServiceForTesting extends UserServiceImpl {
    public UserServiceForTesting(UserStoreForTesting userStorageForTesting) {
        super(userStorageForTesting);
    }

    public void clear() {
        ((UserStoreForTesting)super.userStorage).clear();
    }
}
