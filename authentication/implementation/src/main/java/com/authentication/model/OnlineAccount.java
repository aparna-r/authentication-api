package com.authentication.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class OnlineAccount {
    private final String ownerId;
    private final String username;
    private final String password;
}
