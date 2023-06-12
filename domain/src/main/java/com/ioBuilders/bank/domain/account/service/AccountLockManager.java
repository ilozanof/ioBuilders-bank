package com.ioBuilders.bank.domain.account.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
public class AccountLockManager {

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
    private static final Map<String, AccountLock> ACCOUNT_LOCKS = new HashMap<>();

    public static synchronized void lockAccount(String accountId) {
        AccountLock accountLock = ACCOUNT_LOCKS.containsKey(accountId)
                ? ACCOUNT_LOCKS.get(accountId)
                : new AccountLock();
        accountLock.increaseThreads();
        ACCOUNT_LOCKS.put(accountId, accountLock);
        accountLock.lock.lock();
    }

    public static synchronized void releaseAccount(String accountId) {
        AccountLock accountLock =  ACCOUNT_LOCKS.get(accountId);
        accountLock.lock.unlock();
        accountLock.decreaseThreads();
        if (accountLock.numThreads == 0) {
            ACCOUNT_LOCKS.remove(accountId);
        }
    }
}
