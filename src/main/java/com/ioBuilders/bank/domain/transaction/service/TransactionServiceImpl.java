package com.ioBuilders.bank.domain.transaction.service;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.ports.out.AccountStorage;
import com.ioBuilders.bank.domain.account.service.AccountLockManager;
import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionException;
import com.ioBuilders.bank.domain.transaction.ports.out.TransactionStorage;
import com.ioBuilders.bank.domain.transaction.ports.in.TransactionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class TransactionServiceImpl implements TransactionService {

    private AccountStorage accountStorage;
    protected TransactionStorage transactionStorage;

    public TransactionServiceImpl(AccountStorage accountStorage,
                                  TransactionStorage transactionStorage) {
        this.accountStorage = accountStorage;
        this.transactionStorage = transactionStorage;
    }

    @Transactional
    @Override
    public void executeTransaction(Transaction transaction) {

        // we LOCK Both Accounts:
        AccountLockManager.lockAccount(transaction.getOriginAccountId());
        AccountLockManager.lockAccount(transaction.getDestinationAccountId());

        // We get info about both Accounts:
        Optional<Account> originAccount = this.accountStorage.getAccountForUpdate(transaction.getOriginAccountId());
        Optional<Account> destinationAccount = this.accountStorage.getAccountForUpdate(transaction.getDestinationAccountId());
        boolean originInThisBank = originAccount.isPresent();
        boolean destinationInThisBank = destinationAccount.isPresent();

        // At least one of the Accounts MUST belong to this Bank:
        if(!originInThisBank && !destinationInThisBank) {
            throw new TransactionException(TransactionException.BOTH_EXTERNAL_ACCOUNTS);
        }

        // We check balance if origin belongs to this bank:
        if (originInThisBank && originAccount.get().getBalance() < transaction.getAmount()) {
            throw new TransactionException(TransactionException.NOT_ENOUGH_BALANCE);
        }

        // We check both Accounts are NOT the same:
        if (transaction.getOriginAccountId().equalsIgnoreCase(transaction.getDestinationAccountId())) {
            throw new TransactionException(TransactionException.TRANSACTION_TO_SAME_ACCOUNT);
        }

        // We save the Transaction:
        this.transactionStorage.createTransaction(transaction);

        // We update Origin balance, if the account belongs to this Bank
        if (originInThisBank) {
            this.accountStorage.updateBalance(transaction.getOriginAccountId(), -transaction.getAmount());
        }

        // We update Destination balance, if the account belongs to this Bank
        if (destinationInThisBank) {
            this.accountStorage.updateBalance(transaction.getDestinationAccountId(), transaction.getAmount());
        }

        // We UNLOCK both Accounts:
        AccountLockManager.releaseAccount(transaction.getOriginAccountId());
        AccountLockManager.releaseAccount(transaction.getDestinationAccountId());
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountId, int page) {
        return this.transactionStorage.getTransactionsByAccount(accountId, page);
    }
}
