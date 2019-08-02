package com.authentication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "jwt.token")
@Getter
@Setter
public class TokenProperties {
    private String issuer;
    private Duration ttl;
    private String secret;
}
