package com.example.ioBuilders.bank.domain.account;

import java.util.List;
import java.util.Optional;

public interface AccountStorage {
    String createAccount(String dni, String iban);
    Optional<Account> getAccount(String iban);
    List<Account> getAccounts(String dni);
    List<Account> getAllAccounts();
    void clear();
}
