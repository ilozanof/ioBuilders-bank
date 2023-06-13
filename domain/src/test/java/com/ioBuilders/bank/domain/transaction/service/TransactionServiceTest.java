package com.ioBuilders.bank.domain.transaction.service;



import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.ports.in.AccountService;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;

import com.ioBuilders.bank.domain.transaction.ports.out.TransactionStorage;
import com.ioBuilders.bank.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;


import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    private Logger log = LoggerFactory.getLogger(TransactionServiceTest.class);

    @InjectMocks
    private TransactionServiceImpl transactionService;
    @Mock
    private AccountService accountService;
    @Mock
    private TransactionStorage transactionStorage;

    @Test
    public void givenSeveralTransactions_thenICanExecuteThemInParallel() throws Exception {

        // We define some constants:
        final Float     INITIAL_DEPOSIT = 1000.0F;
        final Integer   NUM_TRANSFERS_ALICE_TO_BOB = 10;
        final Integer   NUM_TRANSFERS_BOB_TO_ALICE = 10;
        final Float     AMOUNT_TRANSFER_BOB_TO_ALICE = 5f;
        final Float     AMOUNT_TRANSFER_ALICE_TO_BOB = 10f;
        final Integer   NUM_THREADS = 10;

        final Float EXPECTED_BALANCE_ALICE = INITIAL_DEPOSIT
                - (NUM_TRANSFERS_ALICE_TO_BOB * AMOUNT_TRANSFER_ALICE_TO_BOB)
                + (NUM_TRANSFERS_BOB_TO_ALICE * AMOUNT_TRANSFER_BOB_TO_ALICE);

        final Float EXPECTED_BALANCE_BOB = INITIAL_DEPOSIT
                - (NUM_TRANSFERS_BOB_TO_ALICE * AMOUNT_TRANSFER_BOB_TO_ALICE)
                + (NUM_TRANSFERS_ALICE_TO_BOB * AMOUNT_TRANSFER_ALICE_TO_BOB);


        User aliceUSer = new User("A0", "Alice");
        User bobUSer = new User("B0", "Bob");

        Account aliceAccount = new Account(aliceUSer, "IBAN-A-1", INITIAL_DEPOSIT);
        Account bobAccount = new Account(bobUSer, "IBAN-B-1", INITIAL_DEPOSIT);

        Map<String, AtomicLong> BALANCES = new HashMap<>() {{
            put(aliceAccount.getAccountId(), new AtomicLong((long) aliceAccount.getBalance()));
            put(bobAccount.getAccountId(), new AtomicLong((long) bobAccount.getBalance()));
        }};

        when(accountService.isAccountValid(any())).thenReturn(true);
        when(accountService.getAccountAndLock(aliceAccount.getAccountId())).thenReturn(Optional.of(aliceAccount));
        when(accountService.getAccountAndLock(bobAccount.getAccountId())).thenReturn(Optional.of(bobAccount));

        doAnswer(req -> {
            BALANCES.get(req.getArgument(0)).addAndGet(((Float) req.getArgument(1)).longValue());
            return null;
        }).when(accountService).updateBalance(anyString(), anyFloat());

        when(transactionStorage.createTransaction(any())).thenReturn(0);

        Runnable bobSendsToAlice = () -> transactionService
                .executeTransaction(new TransactionRequest(bobAccount.getAccountId(), aliceAccount.getAccountId(), AMOUNT_TRANSFER_BOB_TO_ALICE, new Date()));
        Runnable aliceSendsToBob = () -> transactionService
                .executeTransaction(new TransactionRequest(aliceAccount.getAccountId(), bobAccount.getAccountId(), AMOUNT_TRANSFER_ALICE_TO_BOB, new Date()));

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        int maxIterations = Math.max(NUM_TRANSFERS_ALICE_TO_BOB, NUM_TRANSFERS_BOB_TO_ALICE);
        int numTxsBobToAlice = 0;
        int numTxsAliceToBob = 0;

        for (int i = 0; i < maxIterations; i++) {
            if (numTxsBobToAlice < NUM_TRANSFERS_BOB_TO_ALICE) {
                log.info("Sending {} from Bob To Alice...", AMOUNT_TRANSFER_BOB_TO_ALICE);
                numTxsBobToAlice++;
                executor.submit(bobSendsToAlice);
            }
            if (numTxsAliceToBob < NUM_TRANSFERS_ALICE_TO_BOB) {
                log.info("Sending {} from Alice to Bob...", AMOUNT_TRANSFER_ALICE_TO_BOB);
                numTxsAliceToBob++;
                executor.submit(aliceSendsToBob);
            }
        }
        executor.awaitTermination(5, TimeUnit.SECONDS);

        log.info("Alice Expected Balance: {}", EXPECTED_BALANCE_ALICE);
        log.info("Alice Balance: {}", BALANCES.get(aliceAccount.getAccountId()));
        log.info("Bob Expected Balance: {}", EXPECTED_BALANCE_BOB);
        log.info("Bob Balance: {}", BALANCES.get(bobAccount.getAccountId()));

        assertEquals(EXPECTED_BALANCE_ALICE, BALANCES.get(aliceAccount.getAccountId()).floatValue());
        assertEquals(EXPECTED_BALANCE_BOB, BALANCES.get(bobAccount.getAccountId()).floatValue());
    }

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
