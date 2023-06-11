package com.example.ioBuilders.bank.storage.account;

import com.example.ioBuilders.bank.domain.account.Account;
import com.example.ioBuilders.bank.domain.account.AccountException;
import com.example.ioBuilders.bank.domain.account.AccountStorage;

import com.example.ioBuilders.bank.storage.user.UserEntity;
import com.example.ioBuilders.bank.storage.user.UserJpaStore;

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
    private AccountJpaStore accountJpaStore;
    private UserJpaStore userJpaStore;

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
        if (this.accountJpaStore.findByIban(iban).isPresent()) {
            throw new AccountException(AccountException.ACCOUNT_ALREADY_CREATED);
        }
        return this.accountJpaStore.save(new AccountEntity(iban, user.get())).getIban();
    }

    @Override
    public List<Account> getAllAccounts() {
        return this.accountJpaStore.findAll().stream().map(AccountEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Account> getAccount(String iban) {
        Optional<AccountEntity> accountEntity = this.accountJpaStore.findByIban(iban);
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
    public void clear() {
        this.accountJpaStore.deleteAll();
    }
}
