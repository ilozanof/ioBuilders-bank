package com.ioBuilders.bank.domain.account.ports.in;

import com.ioBuilders.bank.domain.account.model.Account;

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
