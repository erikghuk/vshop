package com.elg.vshop.service.security.jwt.dto;

import com.elg.vshop.validator.password.PasswordFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AuthRequestDto {
    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "{name.not.empty}")
    @Email
    private String email;

    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "{name.not.empty}")
    private String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
