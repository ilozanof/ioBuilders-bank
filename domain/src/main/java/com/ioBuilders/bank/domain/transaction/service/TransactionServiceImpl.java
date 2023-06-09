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
 *
 * An implementation of {@link TransactionService}
 * This class implements Concurrency art ACCOUNT-Level, not at Class Level or using a Local Lock.
 * For every pair of Accounts involved in a Transaction, a LOCK is created and held. When the Tx is done, the Lock is
 * released. The Lock also keeps tracxk of the number of Transactions trying to exdecugte suing the same Pair of
 * Accounts.
 *
 * The Locking logic is implemented in {@link AccountLockManager}
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

    /**
     * It executes a Tx, updating both Balances and running sanity checks.
     * This method assumes that it is Thread-Safe (Calls will go through "executeTransaction", which is the one that
     * acquires the Lock.
     */
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

    /**
     * It executes the Transaction, moving money between 2 Accounts, applying a LOCK at the Pair of Txs so multiple
     * Txs can be executed safely in a Multi-Thread environment.
     */
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

        AccountLockManager.getAccountLock(
                transactionReq.getOriginAccountId(),
                transactionReq.getDestinationAccountId())
                .lock();

        int transactionId = runTransactionSafely(transactionReq);

        // We UNLOCK both Accounts:
        AccountLockManager.releaseAccount(
                transactionReq.getOriginAccountId(),
                transactionReq.getDestinationAccountId());


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
