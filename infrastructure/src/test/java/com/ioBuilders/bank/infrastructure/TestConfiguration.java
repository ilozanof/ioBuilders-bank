package com.ioBuilders.bank.infrastructure;


import com.ioBuilders.bank.infrastructure.account.storage.AccountJpaStore;
import com.ioBuilders.bank.infrastructure.account.storage.AccountStoreForTesting;
import com.ioBuilders.bank.infrastructure.transaction.storage.TransactionJpaStore;
import com.ioBuilders.bank.infrastructure.transaction.storage.TransactionStoreForTesting;
import com.ioBuilders.bank.infrastructure.user.storage.UserJpaStore;
import com.ioBuilders.bank.infrastructure.user.storage.UserStoreForTesting;
import org.springframework.context.annotation.Bean;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {
    @Bean
    public UserStoreForTesting userStoreForTesting(UserJpaStore userJpaStore) {
        return new UserStoreForTesting(userJpaStore);
    }

    @Bean
    public AccountStoreForTesting accountStoreForTesting(AccountJpaStore accountJpaStore,
                                                         UserJpaStore userJpsStore) {
        return new AccountStoreForTesting(accountJpaStore, userJpsStore);
    }

    @Bean
    public TransactionStoreForTesting transactionStoreForTesting(TransactionJpaStore transactionJpaStore) {
        return new TransactionStoreForTesting(transactionJpaStore);
    }

}
