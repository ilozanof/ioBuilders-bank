package com.ioBuilders.bank.domain.account.service;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.model.AccountException;
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
    public boolean isAccountValid(String accountId) {
        if (accountId.startsWith("EXTERNAL-")) return true;
        Optional<Account> account = this.getAccount(accountId);
        return account.isPresent();
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
    public Optional<Account> getAccountAndLock(String accountId) {
        return this.accountStorage.getAccountForUpdate(accountId);
    }

    @Override
    public List<Account> getAccounts(String dni) {
        return this.accountStorage.getAccounts(dni);
    }

    @Override
    public void updateBalance(String accountId, float delta) {
        this.accountStorage.updateBalance(accountId, delta);
        if (this.accountStorage.getAccount(accountId).get().getBalance() < 0) {
            throw new AccountException(AccountException.ACCOUNT_BALANCE_NEGATIVE);
        }
    }
}
