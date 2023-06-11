package com.example.ioBuilders.bank.domain.account;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * Operations allowed on Accounts
 */
public interface AccountService {
    List<Account> getAllAccounts();
    String createAccount(String dni);
    Optional<Account> getAccount(String iban);
    List<Account> getAccounts(String dni);
}
