package com.example.ioBuilders.bank.domain.transaction;

import com.example.ioBuilders.bank.domain.account.Account;
import com.example.ioBuilders.bank.domain.account.AccountStorage;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
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

        // We get info about both Accounts:
        Optional<Account> originAccount = this.accountStorage.getAccount(transaction.getOriginAccountId());
        Optional<Account> destinationAccount = this.accountStorage.getAccount(transaction.getDestinationAccountId());
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
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountId, int page) {
        return this.transactionStorage.getTransactionsByAccount(accountId, page);
    }
}
