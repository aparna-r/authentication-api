package com.authentication.exceptions;

import com.authentication.exceptions.AppException.*;
import com.authentication.exceptions.Error.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Error> handleException(final Throwable t) {
        log.error("unknown error", t);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Error.of(Code.UNKNOWN, "internal error"));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleHttpClientErrorException(final HttpClientErrorException ex) {
        log.error("upstream http client error", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Error.of(Code.UPSTREAM_FAILURE, "upstream error"));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleRestClientErrorException(final RestClientException ex) {
        log.error("upstream rest client error", ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Error.of(Code.UPSTREAM_FAILURE, "service unavailable"));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleUnknownBankAccountException(final UnknownBankAccountException ex) {
        log.debug("unknown bank account error", ex);
        return ResponseEntity.badRequest().body(Error.of(Code.UNKNOWN_ENTITY, ""));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleAccountAlreadyExistException(final AccountAlreadyExistException ex) {
        log.debug("account already exist", ex);
        return ResponseEntity.badRequest().body(Error.of(Code.DUPLICATE_ENTITY, ""));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleUsernameAlreadyExistException(final UsernameAlreadyExistException ex) {
        log.debug("username already exist", ex);
        return ResponseEntity.badRequest().body(Error.of(Code.DUPLICATE_ENTITY, ""));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleInvalidCredentialException(final InvalidCredentialException ex) {
        log.debug("invalid credential", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Error.of(Code.INVALID_CREDENTIAL, ""));
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleBlockedAccountException(final BlockedAccountException ex) {
        log.debug("blocked account", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Error.of(Code.BLOCKED, ""));
    }
}
