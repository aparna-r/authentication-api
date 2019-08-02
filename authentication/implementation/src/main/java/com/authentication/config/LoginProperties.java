package com.authentication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "login")
@Getter
@Setter
public class LoginProperties {
    private int maxFailedAttempts = 3;
    private Duration failedAttemptsDuration = Duration.ofMinutes(30);
    private Duration blockDuration = Duration.ofHours(24);
    private long cacheSize = 1000;
}
