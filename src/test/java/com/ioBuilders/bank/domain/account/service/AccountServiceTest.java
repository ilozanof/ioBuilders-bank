package com.ioBuilders.bank.domain.account.service;

import com.ioBuilders.bank.app.rest.BankApplication;
import com.ioBuilders.bank.domain.account.service.AccountServiceImpl;
import com.ioBuilders.bank.infrastructure.account.storage.AccountStore;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author i.fernandez@nchain.com
 *
 * Testiung Suite for the Account Store Adapter (H2 in-memory DB)
 */
@SpringBootTest(classes = BankApplication.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountStore accountStore;

    @Test
    public void givenAccountsAreCreated_thenWeCanRetrieveThem() {
        // Does this make any sense??????
    }
}
