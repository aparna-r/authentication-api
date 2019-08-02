package com.authentication.controller;

import com.authentication.dto.AccessToken;
import com.authentication.dto.Credential;
import com.authentication.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final TokenService tokenService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AccessToken authenticateUser(@RequestBody @Valid Credential credential) {
        return AccessToken.from(tokenService.createToken(credential));
    }
}
