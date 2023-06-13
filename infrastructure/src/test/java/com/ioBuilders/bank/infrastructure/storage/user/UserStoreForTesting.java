package com.ioBuilders.bank.infrastructure.storage.user;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.fernandez@nchain.com
 *
 * This Store adds other meethods useful for testing but too "dangerous" to be included in the "main" codebase.
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
