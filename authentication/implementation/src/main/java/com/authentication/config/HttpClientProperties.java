package com.authentication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "http.client")
@Getter
@Setter
class HttpClientProperties {
    private int maxConnection = 10;
    private int maxConnectionPerRoute = 10;
    private Duration connectionTimeout = Duration.ofSeconds(10);
    private Duration readTimeout = Duration.ofSeconds(10);
}
