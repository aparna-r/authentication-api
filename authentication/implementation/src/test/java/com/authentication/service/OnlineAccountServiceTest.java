package com.authentication.service;

import com.authentication.dao.OnlineAccountDao;
import com.authentication.model.BankAccount;
import com.authentication.model.OnlineAccount;
import com.authentication.upstream.BankAccountApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OnlineAccountServiceTest {
    @Mock
    private OnlineAccountDao onlineAccountDao;
    @Mock
    private BankAccountApiClient bankAccountApiClient;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private BankAccount bankAccount;
    @Captor
    private ArgumentCaptor<OnlineAccount> onlineAccountArgumentCaptor;

    @InjectMocks
    private OnlineAccountServiceImpl objectUnderTest;

    @Test
    public void testCreateAccountForSuccess() {
        when(bankAccountApiClient.getBankAccount("testAccount")).thenReturn(Optional.of(bankAccount));
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedtestPassword");
        when(bankAccount.getOwnerId()).thenReturn("testOwner");
        doNothing().when(onlineAccountDao).create(any(OnlineAccount.class));

        objectUnderTest.createAccount("testAccount", "testUsername", "testPassword");

        verify(onlineAccountDao, times(1)).create(onlineAccountArgumentCaptor.capture());

        assertThat(onlineAccountArgumentCaptor.getValue())
                .extracting(OnlineAccount::getOwnerId, OnlineAccount::getUsername, OnlineAccount::getPassword)
                .containsExactly("testOwner", "testUsername", "encodedtestPassword");

        verify(bankAccount, times(1)).getOwnerId();
        verify(passwordEncoder, times(1)).encode("testPassword");
        verify(bankAccountApiClient, times(1)).getBankAccount("testAccount");

        verifyNoMoreInteractions(bankAccountApiClient, passwordEncoder, bankAccount, onlineAccountDao);
    }
}
