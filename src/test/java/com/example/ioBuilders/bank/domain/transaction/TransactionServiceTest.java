package com.example.ioBuilders.bank.domain.transaction;

import com.example.ioBuilders.bank.TestConfiguration;
import com.example.ioBuilders.bank.domain.account.Account;
import com.example.ioBuilders.bank.domain.account.AccountService;
import com.example.ioBuilders.bank.domain.account.AccountServiceForTesting;
import com.example.ioBuilders.bank.domain.user.User;
import com.example.ioBuilders.bank.domain.user.UserService;
import com.example.ioBuilders.bank.domain.user.UserServiceForTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Date;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@SpringBootTest
@Import(TestConfiguration.class)
public class TransactionServiceTest {
    @Autowired
    private TransactionServiceForTesting transactionService;
    @Autowired
    private UserServiceForTesting userService;
    @Autowired
    private AccountServiceForTesting accountService;

    @BeforeEach
    private void setup() {
        transactionService.clear();
        accountService.clear();
        userService.clear();
    }

    @Test
    public void given2LocalAccounts_thenICanMoveMoneyBetweenThem() {
        User user1 = new User("Alice-DNI", "Alice");
        User user2 = new User("Bob-DNI", "Bob");

        userService.createUser(user1);
        userService.createUser(user2);
        String account1Id = accountService.createAccount(user1.getDni());
        String account2Id = accountService.createAccount(user2.getDni());

        Transaction transaction0 = new Transaction("External Account", account1Id, 50, new Date());
        transactionService.executeTransaction(transaction0);

        Transaction transaction1 = new Transaction(account1Id, account2Id, 50, new Date());
        transactionService.executeTransaction(transaction1);
    }
}
