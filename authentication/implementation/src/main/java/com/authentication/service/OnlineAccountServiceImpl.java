package com.authentication.service;

import com.authentication.dao.OnlineAccountDao;
import com.authentication.exceptions.AppException;
import com.authentication.model.BankAccount;
import com.authentication.model.OnlineAccount;
import com.authentication.upstream.BankAccountApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnlineAccountServiceImpl implements OnlineAccountService {
    private final OnlineAccountDao onlineAccountDao;
    private final BankAccountApiClient bankAccountApiClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createAccount(final String accountNumber, final String username, final String password) {
        final Optional<BankAccount> bankAccountOp = bankAccountApiClient.getBankAccount(accountNumber);
        if (bankAccountOp.isPresent()) {
            final BankAccount bankAccount = bankAccountOp.get();
            final String encodedPassword = passwordEncoder.encode(password);
            final OnlineAccount onlineAccount = OnlineAccount.of(bankAccount.getOwnerId(), username, encodedPassword);
            onlineAccountDao.create(onlineAccount);
        } else {
            throw new AppException.UnknownBankAccountException();
        }
    }
}
