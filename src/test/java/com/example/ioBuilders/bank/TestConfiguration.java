package com.example.ioBuilders.bank;

import com.example.ioBuilders.bank.domain.account.AccountServiceForTesting;
import com.example.ioBuilders.bank.domain.transaction.TransactionServiceForTesting;
import com.example.ioBuilders.bank.domain.user.UserServiceForTesting;
import com.example.ioBuilders.bank.storage.account.AccountJpaStore;
import com.example.ioBuilders.bank.storage.account.AccountStoreForTesting;
import com.example.ioBuilders.bank.storage.transaction.TransactionJpaStore;
import com.example.ioBuilders.bank.storage.transaction.TransactionStoreForTesting;
import com.example.ioBuilders.bank.storage.user.UserJpaStore;
import com.example.ioBuilders.bank.storage.user.UserStoreForTesting;
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
    public UserServiceForTesting userServiceForTesting(UserStoreForTesting userStoreForTesting) {
        return new UserServiceForTesting(userStoreForTesting);
    }
    @Bean
    public AccountStoreForTesting accountStoreForTesting(AccountJpaStore accountJpaStore,
                                                         UserJpaStore userJpsStore) {
        return new AccountStoreForTesting(accountJpaStore, userJpsStore);
    }
    @Bean
    public AccountServiceForTesting accountServiceForTesting(AccountStoreForTesting accountStoreForTesting) {
        return new AccountServiceForTesting(accountStoreForTesting);
    }
    @Bean
    public TransactionStoreForTesting transactionStoreForTesting(TransactionJpaStore transactionJpaStore) {
        return new TransactionStoreForTesting(transactionJpaStore);
    }
    @Bean
    public TransactionServiceForTesting transactionServiceForTesting(AccountStoreForTesting accountStoreForTesting,
                                                                     TransactionStoreForTesting transactionStoreForTesting) {
        return new TransactionServiceForTesting(accountStoreForTesting, transactionStoreForTesting);
    }
}
