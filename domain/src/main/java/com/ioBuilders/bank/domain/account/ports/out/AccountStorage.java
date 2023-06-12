package com.ioBuilders.bank.domain.account.ports.out;

import com.ioBuilders.bank.domain.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountStorage {
    String createAccount(String dni, String iban);
    Optional<Account> getAccount(String iban);
    Optional<Account> getAccountForUpdate(String iban);
    List<Account> getAccounts(String dni);
    List<Account> getAllAccounts();
    void updateBalance(String iban, float deltaAmount);
}
