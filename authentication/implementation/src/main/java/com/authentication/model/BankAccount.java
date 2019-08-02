package com.authentication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BankAccount {
    private final String iban;
    private final String ownerId;

    @JsonCreator
    public BankAccount(@JsonProperty("iban") final String iban, @JsonProperty("ownerId") final String ownerId) {
        this.iban = iban;
        this.ownerId = ownerId;
    }
}
