package com.ioBuilders.bank.infrastructure.storage.account;

import com.ioBuilders.bank.infrastructure.storage.user.UserJpaStore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author i.fernandez@nchain.com
 *
 * This Store adds other meethods useful for testing but too "dangerous" to be included in the "main" codebase.
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
