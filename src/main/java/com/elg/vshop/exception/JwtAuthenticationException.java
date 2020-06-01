package com.elg.vshop.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
