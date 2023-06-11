package com.example.ioBuilders.bank.rest.account;

import com.example.ioBuilders.bank.domain.account.Account;
import com.example.ioBuilders.bank.domain.account.AccountService;
import com.example.ioBuilders.bank.domain.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    /**
     * We check Response Status is OK when retrieve an Existing Account
     * @throws Exception
     */
    @Test
    public void whenGetExistingAccount_thenStatusIsOK() throws Exception {
        Account account = new Account("IBAN-xxxxxx", 10);
        when(accountService.getAccount(any())).thenReturn(Optional.of(account));
        mockMvc.perform(get("/v1/accounts/IBAN-xxxxxx").contentType("application/json")).andExpect(status().isOk());
    }

    /**
     * We check Response Status is 404 when retrieve a Non-Existing Account
     * @throws Exception
     */
    @Test
    public void whenGetNonExistingUser_thenStatusIsNOTFOUND() throws Exception {
        when(accountService.getAccount(any())).thenReturn(Optional.empty());
        mockMvc.perform(get("/v1/accounts/Dummy").contentType("application/json")).andExpect(status().isNotFound());
    }

    @Test
    public void whenGetAllAccounts_thenStatusIsOK() throws Exception {
        mockMvc.perform(get("/v1/accounts").contentType("application/json")).andExpect(status().isOk());
    }

}
