package com.ioBuilders.bank.domain.user.model;

import com.ioBuilders.bank.domain.account.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author i.fernandez@nchain.com
 *
 * A User represents a physical person, a Client of the Bank.
 * This class is immutable
 */
@AllArgsConstructor
@Data
public final class User {
    private final String dni;
    private final String name;
    private final List<Account> accounts;

    public User(String dni, String name) {
        this(dni, name, new ArrayList<>());
    }
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(this.accounts);
    }
}
