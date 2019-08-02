package com.authentication.controller;

import com.authentication.dto.CreateAccountRequest;
import com.authentication.service.OnlineAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OnlineAccountController {
    private final OnlineAccountService onlineAccountService;

    @PostMapping(value = "/online-accounts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAccount(@RequestBody @Valid final CreateAccountRequest createAccountRequest) {
        onlineAccountService.createAccount(
                createAccountRequest.getAccountNumber(),
                createAccountRequest.getUsername(),
                createAccountRequest.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
