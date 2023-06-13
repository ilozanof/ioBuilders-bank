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
    boolean isAccountValid(String accountId);
    List<Account> getAllAccounts();
    String createAccount(String dni);
    Optional<Account> getAccount(String accountId);
    Optional<Account> getAccountAndLock(String accountId);
    List<Account> getAccounts(String dni);
    void updateBalance(String accountId, float delta);
}
