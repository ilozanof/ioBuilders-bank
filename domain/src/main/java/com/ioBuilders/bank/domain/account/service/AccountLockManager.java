package com.ioBuilders.bank.domain.account.service;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author i.fernandez@nchain.com
 * Copyright (c) 2018-2023 nChain Ltd
 */
@Slf4j
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

    private static String getKeyForAccountPai(String accountId1, String accountId2) {
        return (accountId1.compareTo(accountId2) >= 0)
                ? accountId1 + "-" + accountId2
                : accountId2 + "-" + accountId1;
    }

    public static synchronized Lock getAccountLock(String accountId1, String accountId2) {
        //log.debug("Locking Accounts [{}]-[{}] ... ", accountId1, accountId2);
        String key = getKeyForAccountPai(accountId1, accountId2);
        AccountLock accountLock = ACCOUNT_LOCKS.containsKey(key)
                ? ACCOUNT_LOCKS.get(key)
                : new AccountLock();
        accountLock.increaseThreads();
        ACCOUNT_LOCKS.put(key, accountLock);
        return accountLock.lock;
    }

    public static synchronized void releaseAccountLock(String accountId1, String accountId2) {
        //log.debug("Releasing Accounts [{}] - [{}]... ", accountId1, accountId2);
        String key = getKeyForAccountPai(accountId1, accountId2);
        AccountLock accountLock =  ACCOUNT_LOCKS.get(key);
        accountLock.lock.unlock();
        accountLock.decreaseThreads();
        ACCOUNT_LOCKS.put(key, accountLock);
        if (accountLock.numThreads == 0) {
            ACCOUNT_LOCKS.remove(key);
            //log.debug("Removing Locks for [{}] and [{}] ... ", accountId1, accountId2);
        }
    }
}
