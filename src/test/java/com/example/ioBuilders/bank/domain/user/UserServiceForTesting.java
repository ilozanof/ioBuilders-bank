package com.example.ioBuilders.bank.domain.user;

import com.example.ioBuilders.bank.storage.user.UserStoreForTesting;

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
