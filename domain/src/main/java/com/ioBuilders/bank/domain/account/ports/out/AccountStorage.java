package com.ioBuilders.bank.domain.account.ports.out;

import com.ioBuilders.bank.domain.account.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * A PORT that provides Storage capabilities for Accounts
 */
public interface AccountStorage {
    /** Saves a new Account */
    String createAccount(String dni, String accountId);

    /** Retrieves a specific Account */
    Optional<Account> getAccount(String accountId);

    /** Retrieves a specific Account and it Locks it to avoid Concurrency issues */
    Optional<Account> getAccountForUpdate(String accountId);

    /** Returns all the Accounts belonging to a User */
    List<Account> getAccounts(String dni);

    /** Returns al the Accounts in the Bank (should be paginated) */
    List<Account> getAllAccounts();
    void updateBalance(String accountId, float deltaAmount);
}
