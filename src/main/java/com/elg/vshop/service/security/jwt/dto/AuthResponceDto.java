package com.elg.vshop.service.security.jwt.dto;

import java.util.Date;

public class AuthResponceDto {
    private int id;
    private String jwtToken;
    private String userName;

    public AuthResponceDto(int id, String jwtToken, String userName) {
        this.id = id;
        this.jwtToken = jwtToken;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
