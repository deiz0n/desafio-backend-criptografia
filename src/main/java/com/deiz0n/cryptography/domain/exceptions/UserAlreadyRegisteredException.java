package com.deiz0n.cryptography.domain.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String msg) {
        super(msg);
    }

}
