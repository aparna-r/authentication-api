package com.authentication.dao;

import com.authentication.model.OnlineAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OnlineAccountDaoImpl implements OnlineAccountDao {
    @Override
    public void create(final OnlineAccount onlineAccount) {
        DataStore.addAccount(onlineAccount);
    }

    @Override
    public Optional<OnlineAccount> getByOwnerId(final String ownerId) {
        return DataStore.getAccountByOwnerId(ownerId);
    }

    @Override
    public Optional<OnlineAccount> getByUsername(final String username) {
        return DataStore.getAccountByUsername(username);
    }
}
