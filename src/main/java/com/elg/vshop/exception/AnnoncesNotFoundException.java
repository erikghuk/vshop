package com.elg.vshop.exception;

import javassist.NotFoundException;

public class AnnoncesNotFoundException extends NotFoundException {
    public AnnoncesNotFoundException(String msg) {
        super(msg);
    }

    public AnnoncesNotFoundException(String msg, Exception e) {
        super(msg, e);
    }
}
