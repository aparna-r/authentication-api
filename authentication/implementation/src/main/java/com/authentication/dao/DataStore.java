package com.authentication.dao;


import com.authentication.exceptions.AppException;
import com.authentication.model.OnlineAccount;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class DataStore {
    private static final Lock lock = new ReentrantLock();

    private static final Map<String, OnlineAccount> ownerIdToAccountDetails = new ConcurrentHashMap<>();
    private static final Map<String, OnlineAccount> userNameToAccountDetails = new ConcurrentHashMap<>();

    static Optional<OnlineAccount> getAccountByUsername(final String username) {
        return Optional.ofNullable(userNameToAccountDetails.get(username));
    }

    static Optional<OnlineAccount> getAccountByOwnerId(final String ownerId) {
        return Optional.ofNullable(ownerIdToAccountDetails.get(ownerId));
    }

    static void addAccount(final OnlineAccount onlineAccount) {
        lock.lock();
        try {
            if (ownerIdToAccountDetails.containsKey(onlineAccount.getOwnerId())) {
                throw new AppException.AccountAlreadyExistException("account exist for owner: " + onlineAccount.getOwnerId());
            }

            if (userNameToAccountDetails.containsKey(onlineAccount.getUsername())) {
                throw new AppException.UsernameAlreadyExistException();
            }

            ownerIdToAccountDetails.putIfAbsent(onlineAccount.getOwnerId(), onlineAccount);
            userNameToAccountDetails.putIfAbsent(onlineAccount.getUsername(), onlineAccount);
        } finally {
            lock.unlock();
        }
    }
}
