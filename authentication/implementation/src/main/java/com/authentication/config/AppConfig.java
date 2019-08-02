package com.authentication.config;

import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.URL;
import java.security.KeyStore;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${bcrypt.strength:10}") final int strength) {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    @SneakyThrows
    public HttpClient httpClient(final HttpClientProperties httpClientProperties,
                                 final ServerProperties serverProperties) {
        final Ssl ssl = serverProperties.getSsl();
        final URL keyStore = AppConfig.class.getClassLoader().getResource(removeClasspathPrefix(ssl.getKeyStore()));
        final URL trustStore = AppConfig.class.getClassLoader().getResource(removeClasspathPrefix(ssl.getTrustStore()));
        final SSLContext sslContext = SSLContexts.custom()
                .setKeyStoreType(ssl.getKeyStoreType())
                .loadKeyMaterial(keyStore, ssl.getKeyStorePassword().toCharArray(), ssl.getKeyPassword().toCharArray())
                .loadTrustMaterial(trustStore, ssl.getTrustStorePassword().toCharArray())
                .build();
        return HttpClientBuilder.create().setSSLContext(sslContext)
                .setMaxConnTotal(httpClientProperties.getMaxConnection())
                .setMaxConnPerRoute(httpClientProperties.getMaxConnectionPerRoute()).build();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory(
            final HttpClient httpClient, final HttpClientProperties httpClientProperties) {

        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout((int) httpClientProperties.getConnectionTimeout().toMillis());
        requestFactory.setReadTimeout((int) httpClientProperties.getReadTimeout().toMillis());
        return requestFactory;
    }

    @Bean
    public RestTemplate restTemplate(final HttpComponentsClientHttpRequestFactory requestFactory) {
        return new RestTemplate(requestFactory);
    }

    private static String removeClasspathPrefix(final String file) {
        return file.replace("classpath:", "");
    }
}