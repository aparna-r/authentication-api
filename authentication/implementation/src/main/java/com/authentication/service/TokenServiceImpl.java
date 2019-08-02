package com.authentication.service;

import com.authentication.config.LoginProperties;
import com.authentication.config.TokenProperties;
import com.authentication.dao.OnlineAccountDao;
import com.authentication.dto.Credential;
import com.authentication.exceptions.AppException.BlockedAccountException;
import com.authentication.exceptions.AppException.InvalidCredentialException;
import com.authentication.model.OnlineAccount;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TokenServiceImpl implements TokenService {
    private static final Object DUMMY_OBJ = new Object();

    private final OnlineAccountDao onlineAccountDao;
    private final PasswordEncoder passwordEncoder;
    private final TokenProperties tokenProperties;
    private final int maxFailedAttempts;
    private final Cache<String, AtomicInteger> failedAttempts;
    private final Cache<String, Object> blockedOwners;

    public TokenServiceImpl(final OnlineAccountDao onlineAccountDao,
                            final PasswordEncoder passwordEncoder,
                            final TokenProperties tokenProperties,
                            final LoginProperties loginProperties) {
        this.onlineAccountDao = onlineAccountDao;
        this.passwordEncoder = passwordEncoder;
        this.tokenProperties = tokenProperties;
        this.maxFailedAttempts = loginProperties.getMaxFailedAttempts();
        this.failedAttempts = Caffeine.newBuilder()
                .expireAfterWrite(loginProperties.getFailedAttemptsDuration())
                .maximumSize(loginProperties.getCacheSize())
                .build();
        this.blockedOwners = Caffeine.newBuilder()
                .expireAfterWrite(loginProperties.getBlockDuration())
                .maximumSize(loginProperties.getCacheSize())
                .build();
    }

    @Override
    public String createToken(Credential credential) {
        if (blockedOwners.getIfPresent(credential.getUsername()) != null) {
            throw new BlockedAccountException(credential.getUsername() + " is blocked");
        }

        final Optional<OnlineAccount> onlineAccountOp = onlineAccountDao.getByUsername(credential.getUsername());
        if (onlineAccountOp.isPresent()) {
            final OnlineAccount onlineAccount = onlineAccountOp.get();
            if (passwordEncoder.matches(credential.getPassword(), onlineAccount.getPassword())) {
                unregisterAttempt(credential);
                return createJwt(onlineAccount);
            } else {
                registerAttempt(credential);
            }
            throw new InvalidCredentialException("password does not match");
        }
        throw new InvalidCredentialException("username does not exist");
    }

    private void registerAttempt(final Credential credential) {
        final AtomicInteger attempts = failedAttempts.get(credential.getUsername(),
                s -> new AtomicInteger(0));
        if (attempts.incrementAndGet() >= maxFailedAttempts) {
            blockedOwners.get(credential.getUsername(), s -> DUMMY_OBJ);
            failedAttempts.invalidate(credential.getUsername());
        }
    }

    private void unregisterAttempt(final Credential credential) {
        failedAttempts.invalidate(credential.getUsername());
    }

    private String createJwt(final OnlineAccount onlineAccount) {
        final long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer(tokenProperties.getIssuer())
                .setAudience(onlineAccount.getOwnerId())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(Duration.ofMillis(currentTimeMillis)
                        .plus(tokenProperties.getTtl()).toMillis()))
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret())
                .compact();
    }
}
