package com.authentication.upstream;

import com.authentication.model.BankAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class BankAccountApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String accountUri;

    public BankAccountApiClient(final RestTemplate restTemplate,
                                @Value("${upstream.account-api.base-url}") final String baseUrl,
                                @Value("${upstream.account-api.account-uri}") final String accountUri) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.accountUri = accountUri;
    }

    public Optional<BankAccount> getBankAccount(final String accountNumber) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(baseUrl + accountUri, BankAccount.class,
                    accountNumber));
        } catch (final HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND == e.getStatusCode()) {
                return Optional.empty();
            }
            throw e;
        }
    }
}
