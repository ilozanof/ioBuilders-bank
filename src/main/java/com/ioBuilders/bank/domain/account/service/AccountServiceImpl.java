package com.ioBuilders.bank.domain.account.service;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.ports.in.AccountService;
import com.ioBuilders.bank.domain.account.ports.out.AccountStorage;

import java.util.List;
import java.util.Optional;

// TODO: Check MultiThread Stuff!!!!! LOCKS!!!!!!
public class AccountServiceImpl implements AccountService {

    protected AccountStorage accountStorage;
    private Integer nextIban = 0;

    public AccountServiceImpl(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    private String generateAccountId() {
        return "IBAN-xxxx-xxxx-xxx-" + (++nextIban);
    }

    @Override
    public String createAccount(String dni) {
        String iban = generateAccountId();
        return this.accountStorage.createAccount(dni, iban);
    }

    @Override
    public List<Account> getAllAccounts() {
        return this.accountStorage.getAllAccounts();
    }

    @Override
    public Optional<Account> getAccount(String iban) {
        return this.accountStorage.getAccount(iban);
    }
    @Override
    public List<Account> getAccounts(String dni) {
        return this.accountStorage.getAccounts(dni);
    }
}
