package com.example.ioBuilders.bank.domain.account;

import com.example.ioBuilders.bank.domain.account.AccountService;
import com.example.ioBuilders.bank.domain.account.AccountServiceImpl;
import com.example.ioBuilders.bank.storage.account.AccountStore;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author i.fernandez@nchain.com
 *
 * Testiung Suite for the Account Store Adapter (H2 in-memory DB)
 */
@SpringBootTest
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
