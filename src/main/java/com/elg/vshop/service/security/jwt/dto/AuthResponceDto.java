package com.elg.vshop.service.security.jwt.dto;
import com.elg.vshop.entity.user.Role;


public class AuthResponceDto {
    private String jwtToken;
    private String roleName;

    public AuthResponceDto(String jwtToken, String roleName) {
        this.jwtToken = jwtToken;
        this.roleName = roleName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
