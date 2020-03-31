package com.elg.vshop.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class UserExistException extends BadCredentialsException {
    public UserExistException(String msg) {
        super(msg);
    }

    public UserExistException(String msg, Throwable t) {
        super(msg, t);
    }
}
