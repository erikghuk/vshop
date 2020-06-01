package com.elg.vshop.service.security.jwt.dto;

public class MessageClassForJson {
    private String message;

    public MessageClassForJson(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
