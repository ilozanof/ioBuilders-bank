package com.ioBuilders.bank.infrastructure.storage.account;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.model.AccountException;
import com.ioBuilders.bank.domain.account.ports.out.AccountStorage;

import com.ioBuilders.bank.infrastructure.storage.user.UserEntity;
import com.ioBuilders.bank.infrastructure.storage.user.UserJpaStore;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class AccountStore implements AccountStorage {
    protected AccountJpaStore accountJpaStore;
    protected UserJpaStore userJpaStore;

    @Autowired
    public AccountStore(AccountJpaStore accountJpaStore,
                        UserJpaStore userJpaStore) {
        this.accountJpaStore = accountJpaStore;
        this.userJpaStore = userJpaStore;
    }

    @Override
    public String createAccount(String dni, String iban) {
        Optional<UserEntity> user = userJpaStore.findByDni(dni);
        if (user.isEmpty()) {
            throw new AccountException(AccountException.NO_USER);
        }
        if (this.accountJpaStore.findByAccountId(iban).isPresent()) {
            throw new AccountException(AccountException.ACCOUNT_ALREADY_CREATED);
        }
        return this.accountJpaStore.save(new AccountEntity(iban, user.get())).getAccountId();
    }

    @Override
    public List<Account> getAllAccounts() {
        return this.accountJpaStore.findAll().stream().map(AccountEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Account> getAccount(String iban) {
        Optional<AccountEntity> accountEntity = this.accountJpaStore.findByAccountId(iban);
        return accountEntity.isPresent()
                ? Optional.of(accountEntity.get().toDomain())
                : Optional.empty();
    }

    @Override
    public Optional<Account> getAccountForUpdate(String iban) {
        Optional<AccountEntity> accountEntity = this.accountJpaStore.findByAccountIdForUpdate(iban);
        return accountEntity.isPresent()
                ? Optional.of(accountEntity.get().toDomain())
                : Optional.empty();
    }

    @Override
    public List<Account> getAccounts(String dni) {
        Optional<UserEntity> user = userJpaStore.findByDni(dni);
        if (user.isEmpty()) {
            throw new AccountException(AccountException.NO_USER);
        }
        return user.isPresent()
                ? this.accountJpaStore.findByUserId(user.get().getId()).stream().map(AccountEntity::toDomain).collect(Collectors.toList())
                : new ArrayList<>();
    }


    @Override
    public void updateBalance(String iban, float deltaAmount) {
        this.accountJpaStore.updateBalance(iban, deltaAmount);
    }
}
