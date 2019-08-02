package com.authentication.service;

import com.authentication.dto.AccessToken;
import com.authentication.dto.Credential;

public interface TokenService {
    String createToken(Credential credential);
}
