package com.deiz0n.cryptography.domain.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
