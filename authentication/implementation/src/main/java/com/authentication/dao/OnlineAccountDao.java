package com.authentication.dao;

import com.authentication.model.OnlineAccount;

import java.util.Optional;

public interface OnlineAccountDao {
    void create(OnlineAccount onlineAccount);
    Optional<OnlineAccount> getByOwnerId(String ownerId);
    Optional<OnlineAccount> getByUsername(String username);
}
