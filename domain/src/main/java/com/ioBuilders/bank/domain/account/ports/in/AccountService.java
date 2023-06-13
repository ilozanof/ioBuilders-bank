package com.ioBuilders.bank.domain.account.ports.in;

import com.ioBuilders.bank.domain.account.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 *
 * Operations allowed on Accounts at business-level
 */
public interface AccountService {
    /**
     * Check if the Account is Valid (stored in this Bank), or elsewhere (in that case, it ID must comply with some rules)
     */
    boolean isAccountValid(String accountId);

    /** Returns all Accounts in the Bank (should be paginated) */
    List<Account> getAllAccounts();

    /** Creates a new Account for a Given user */
    String createAccount(String dni);

    /** Retrieves the Account by its Id */
    Optional<Account> getAccount(String accountId);

    /** Retrieves an Account an place a Pessimistic Lock to implement Account Locking at DB-Level */
    Optional<Account> getAccountAndLock(String accountId);

    /** Retrieves all Accounts of a given User */
    List<Account> getAccountsByUser(String dni);

    /**
     * Updates the Balance of an Account
     * @param delta increment to ADD to the existing Balance.
     */
    void updateBalance(String accountId, float delta);
}
