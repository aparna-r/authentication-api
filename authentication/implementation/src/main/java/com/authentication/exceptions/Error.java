package com.authentication.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class Error {
    private final Code code;
    @Getter
    private final String message;

    public int getCode() {
        return code == null ? Code.UNKNOWN.value : code.value;
    }

    public enum Code {
        UNKNOWN(100),
        UPSTREAM_FAILURE(101),
        DUPLICATE_ENTITY(102),
        UNKNOWN_ENTITY(103),
        INVALID_CREDENTIAL(104),
        BLOCKED(105);

        Code(final int value) {
            this.value = value;
        }
        @Getter
        private int value;
    }
}
