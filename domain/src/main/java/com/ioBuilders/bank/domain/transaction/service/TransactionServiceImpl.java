package com.ioBuilders.bank.domain.transaction.service;

import com.ioBuilders.bank.domain.account.model.Account;
import com.ioBuilders.bank.domain.account.model.AccountException;
import com.ioBuilders.bank.domain.account.ports.in.AccountService;
import com.ioBuilders.bank.domain.account.service.AccountLockManager;
import com.ioBuilders.bank.domain.transaction.model.Transaction;
import com.ioBuilders.bank.domain.transaction.model.TransactionException;
import com.ioBuilders.bank.domain.transaction.model.TransactionRequest;
import com.ioBuilders.bank.domain.transaction.ports.out.TransactionStorage;
import com.ioBuilders.bank.domain.transaction.ports.in.TransactionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private AccountService accountService;
    protected TransactionStorage transactionStorage;

    public TransactionServiceImpl(AccountService accountService,
                                  TransactionStorage transactionStorage) {
        this.accountService = accountService;
        this.transactionStorage = transactionStorage;
    }

    protected int runTransactionSafely(TransactionRequest transactionReq) {
        log.trace("Tx Running Safely: [id: {}] [from: {} ] [to: {}] [amount:{}] ",
                transactionReq.getId(),
                transactionReq.getOriginAccountId(), transactionReq.getDestinationAccountId(), transactionReq.getAmount());
        // We get info about both Accounts:
        Optional<Account> originAccount = this.accountService.getAccountAndLock(transactionReq.getOriginAccountId());
        Optional<Account> destinationAccount = this.accountService.getAccountAndLock(transactionReq.getDestinationAccountId());
        boolean originInThisBank = originAccount.isPresent();
        boolean destinationInThisBank = destinationAccount.isPresent();

        // At least one of the Accounts MUST belong to this Bank:
        if(!originInThisBank && !destinationInThisBank) {
            throw new TransactionException(TransactionException.BOTH_EXTERNAL_ACCOUNTS);
        }

        // We check balance if origin belongs to this bank:
        if (originInThisBank && originAccount.get().getBalance() < transactionReq.getAmount()) {
            throw new TransactionException(TransactionException.NOT_ENOUGH_BALANCE);
        }

        // We check both Accounts are NOT the same:
        if (transactionReq.getOriginAccountId().equalsIgnoreCase(transactionReq.getDestinationAccountId())) {
            throw new TransactionException(TransactionException.TRANSACTION_TO_SAME_ACCOUNT);
        }

        // We save the Transaction:
        int transactionId = this.transactionStorage.createTransaction(transactionReq);

        // We update Origin balance, if the account belongs to this Bank
        if (originInThisBank) {
            this.accountService.updateBalance(transactionReq.getOriginAccountId(), -transactionReq.getAmount());
        }

        // We update Destination balance, if the account belongs to this Bank
        if (destinationInThisBank) {
            this.accountService.updateBalance(transactionReq.getDestinationAccountId(), transactionReq.getAmount());
        }

        log.trace("Tx Done Safely: [id: {}] [from: {} ] [to: {}] [amount:{}] ",
                transactionReq.getId(),
                transactionReq.getOriginAccountId(), transactionReq.getDestinationAccountId(), transactionReq.getAmount());
        // We return the TransactionId of the transaction created:
        return transactionId;

    }

    @Transactional
    @Override
    public int executeTransaction(TransactionRequest transactionReq) {

        log.debug("Tx Running: [id: {}] [from: {} ] [to: {}] [amount:{}] ",
                transactionReq.getId(),
                transactionReq.getOriginAccountId(), transactionReq.getDestinationAccountId(), transactionReq.getAmount());
        // Sanity Checks:
        if (!this.accountService.isAccountValid(transactionReq.getOriginAccountId())) {
            throw new AccountException(AccountException.ACCOUNT_INVALID, "Origin Account Invalid");
        }

        // Sanity Checks:
        if (!this.accountService.isAccountValid(transactionReq.getDestinationAccountId())) {
            throw new AccountException(AccountException.ACCOUNT_INVALID, "Destination Account Invalid");
        }

        // we LOCK Both Accounts:

            AccountLockManager.getAccountLock(transactionReq.getOriginAccountId(), transactionReq.getDestinationAccountId()).lock();
            //AccountLockManager.getAccountLock(transactionReq.getDestinationAccountId()).lock();



        int transactionId = runTransactionSafely(transactionReq);

        // We UNLOCK both Accounts:

            AccountLockManager.releaseAccountLock(transactionReq.getOriginAccountId(), transactionReq.getDestinationAccountId());
            //AccountLockManager.releaseAccountLock(transactionReq.getDestinationAccountId());


        log.debug("Tx Done: [id: {}] [from: {} ] [to: {}] [amount:{}] ",
                transactionReq.getId(),
                transactionReq.getOriginAccountId(), transactionReq.getDestinationAccountId(), transactionReq.getAmount());
        // We return the TransactionId of the transaction created:
        return transactionId;
    }

    @Override
    public Optional<Transaction> getTransaction(int transactionId) {
        return this.transactionStorage.getTransaction(transactionId);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountId, int page) {
        return this.transactionStorage.getTransactionsByAccount(accountId, page);
    }
}
