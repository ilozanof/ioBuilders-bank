package com.example.ioBuilders.bank.storage.account;

import com.example.ioBuilders.bank.domain.account.Account;
import com.example.ioBuilders.bank.domain.account.AccountException;
import com.example.ioBuilders.bank.domain.account.AccountStorage;
import com.example.ioBuilders.bank.domain.user.User;
import com.example.ioBuilders.bank.storage.user.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@SpringBootTest
public class AccountStoreTest {
    @Autowired
    private AccountStorage accountStore;
    @Autowired
    private UserStore userStore;

    @BeforeEach
    public void setup() {
        this.userStore.clear();
    }

    @Test
    public void givenUserExists_thenICanCreateAccounts() {
        User user = new User("1", "Alice");
        userStore.createUser(user);
        accountStore.createAccount(user.getDni(), "IBAN-1");
        accountStore.createAccount(user.getDni(), "IBAN-2");
    }

    @Test
    public void givenUserNotExists_thenICanNotCreateAccounts() {
        try {
            accountStore.createAccount("dummy DNI", "IBAN-1");
            fail();
        } catch (AccountException e) {
            assertTrue(e.getMessage().equals(AccountException.NO_USER));
        }
    }
}
