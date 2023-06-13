package com.ioBuilders.bank.domain.account.service;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author i.fernandez@nchain.com
 *
 * This Class implemetns a LOCK on a Pair of Transactiosn at Application-level.
 * When a Transaction between 2 Accounts is poerformed, both 2 Accounts must be LOCKED so no other Transaction
 * involving any of the 2 accounts can happen at the samae time.
 * Multiple Transactions between differetn Accounts can happen without limitations.
 *
 */
@Slf4j
public class AccountLockManager {

    /**
     * It represents a Lock on a PAIR of Acounts.
     * It also keeps track opf the number of Threads trying to acquire the Lock.
     */
    static class AccountLock {
        private Lock lock;
        private int numThreads;

        public AccountLock() {
            this.lock = new ReentrantLock();
            this.numThreads = 0;
        }

        public void increaseThreads() { this.numThreads++;}
        public void decreaseThreads() {this.numThreads--;}
    }

    // We keep track of the Pair of Accounts being Locked and the number of Threads trying to work on each Pair.
    // When a Pair of Transaction has NO Thread waiting for their Lock, the entry is removed.
    private static final Map<String, AccountLock> ACCOUNT_LOCKS = new HashMap<>();

    // Given a Pair of Txs, it returns a Key identifier to use in the ACCOUNT_LOCKS MAp.
    private static String getKeyForAccountPai(String accountId1, String accountId2) {
        return (accountId1.compareTo(accountId2) >= 0)
                ? accountId1 + "-" + accountId2
                : accountId2 + "-" + accountId1;
    }

    /**
     * Returns a LOCK on a Pair of Accounts
     */
    public static synchronized Lock getAccountLock(String accountId1, String accountId2) {
        String key = getKeyForAccountPai(accountId1, accountId2);
        AccountLock accountLock = ACCOUNT_LOCKS.containsKey(key)
                ? ACCOUNT_LOCKS.get(key)
                : new AccountLock();
        accountLock.increaseThreads();
        ACCOUNT_LOCKS.put(key, accountLock);
        return accountLock.lock;
    }

    /**
     * Releases the LOCK on a PAIR of Accounts, and remove it altogether iof there ano more Threads trying to
     * acquire it.
     */
    public static synchronized void releaseAccount(String accountId1, String accountId2) {
        String key = getKeyForAccountPai(accountId1, accountId2);
        AccountLock accountLock =  ACCOUNT_LOCKS.get(key);
        accountLock.lock.unlock();
        accountLock.decreaseThreads();
        ACCOUNT_LOCKS.put(key, accountLock);
        if (accountLock.numThreads == 0) {
            ACCOUNT_LOCKS.remove(key);
        }
    }
}
