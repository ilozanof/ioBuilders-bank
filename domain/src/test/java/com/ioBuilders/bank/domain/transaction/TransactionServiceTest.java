package com.ioBuilders.bank.domain.transaction;



import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */

public class TransactionServiceTest {
//    @Autowired
//    private TransactionServiceForTesting transactionService;
//    @Autowired
//    private UserServiceForTesting userService;
//    @Autowired
//    private AccountServiceForTesting accountService;
//
//    @BeforeEach
//    private void setup() {
//        transactionService.clear();
//        accountService.clear();
//        userService.clear();
//    }
//
//    @Test
//    public void given2LocalAccounts_thenICanMoveMoneyBetweenThem() {
//        try {
//            User user1 = new User("Alice-DNI", "Alice");
//            User user2 = new User("Bob-DNI", "Bob");
//
//            userService.createUser(user1);
//            userService.createUser(user2);
//            String account1Id = accountService.createAccount(user1.getDni());
//            String account2Id = accountService.createAccount(user2.getDni());
//
//            Transaction transaction0 = new Transaction("External Account", account1Id, 50, new Date());
//            transactionService.executeTransaction(transaction0);
//
//            Transaction transaction1 = new Transaction(account1Id, account2Id, 50, new Date());
//            transactionService.executeTransaction(transaction1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//    }
}
