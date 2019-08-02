package com.authentication.config;

public class AppConstant {
    public static final int FIELD_MAX_SIZE = 50;
    public static final int PWD_MIN_SIZE = 6;

    public static final String MANDATORY_USERNAME = "username is mandatory";
    public static final String MANDATORY_PASSWORD = "password is mandatory";
    public static final String MANDATORY_ACCOUNT_NUMBER = "accountNumber is mandatory";
    public static final String TOO_LONG_USERNAME = "username is too long";
    public static final String TOO_LONG_ACCOUNT_NUMBER = "accountNumber is too long";
    public static final String INVALID_PASSWORD_LENGTH = "invalid password length (should be between " + PWD_MIN_SIZE
            + " - " + FIELD_MAX_SIZE;
}
