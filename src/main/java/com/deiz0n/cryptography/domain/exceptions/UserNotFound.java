package com.deiz0n.cryptography.domain.exceptions;

public class UserNotFound extends RuntimeException{

    public UserNotFound(String msg) {
        super(msg);
    }
}
