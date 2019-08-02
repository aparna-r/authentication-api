package com.authentication.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.authentication.config.AppConstant.*;

@Getter
public final class CreateAccountRequest {
    @NotBlank(message = MANDATORY_ACCOUNT_NUMBER)
    @Size(max = FIELD_MAX_SIZE, message = TOO_LONG_ACCOUNT_NUMBER)
    private final String accountNumber;
    @NotBlank(message = MANDATORY_USERNAME)
    @Size(max = FIELD_MAX_SIZE, message = TOO_LONG_USERNAME)
    private final String username;
    @NotBlank
    @Size(min = PWD_MIN_SIZE, max = FIELD_MAX_SIZE, message = INVALID_PASSWORD_LENGTH)
    private final String password;

    @JsonCreator
    public CreateAccountRequest(@JsonProperty final String accountNumber, @JsonProperty final String username,
                                @JsonProperty final String password) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;
    }
}
