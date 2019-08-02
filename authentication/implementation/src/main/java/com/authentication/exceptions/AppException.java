package com.authentication.exceptions;

public class AppException extends RuntimeException {

    public AppException(final String message) {
        super(message);
    }

    public AppException() {
    }

    public static class AccountAlreadyExistException extends AppException {
        public AccountAlreadyExistException(String message) {
            super(message);
        }
    }

    public static class UsernameAlreadyExistException extends AppException {
    }

    public static class UnknownBankAccountException extends AppException {
    }

    public static class InvalidCredentialException extends AppException {
        public InvalidCredentialException(String message) {
            super(message);
        }
    }

    public static class BlockedAccountException extends AppException {
        public BlockedAccountException(String message) {
            super(message);
        }
    }
}
