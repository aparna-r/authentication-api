package com.authentication.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.authentication.config.AppConstant.*;

@Getter
public class Credential {
    @NotBlank(message = MANDATORY_USERNAME)
    @Size(max = FIELD_MAX_SIZE, message = TOO_LONG_USERNAME)
    private final String username;
    @NotBlank(message = MANDATORY_PASSWORD)
    @Size(min = PWD_MIN_SIZE, max = FIELD_MAX_SIZE, message = INVALID_PASSWORD_LENGTH)
    private final String password;

    @JsonCreator
    public Credential(@JsonProperty final String username, @JsonProperty String password) {
        this.username = username;
        this.password = password;
    }
}
