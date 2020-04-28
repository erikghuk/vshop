package com.elg.vshop.service.security.jwt.dto;

import com.elg.vshop.validator.password.PasswordFormat;

import javax.validation.constraints.*;

public class RegistratingUserDTO {
    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "{name.not.empty}")
    @Email
    private String email;

    @Size(min = 6, message = "La taille  doit être min 6 simbol")
    @PasswordFormat
    @NotEmpty(message = "{name.not.empty}")
    private String password;

    private String passwordConfirm;

    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "{name.not.empty}")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "{username.not.valid}")
    private String userName;

    public RegistratingUserDTO(@NotNull(message = "{name.not.empty}") @NotEmpty(message = "{name.not.empty}") @Email String email, @Size(min = 6, message = "La taille  doit être min 6 simbol") @NotEmpty(message = "{name.not.empty}") String password, String passwordConfirm, @NotNull(message = "{name.not.empty}") @NotEmpty(message = "{name.not.empty}") @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "{username.not.valid}") String userName) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.userName = userName;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
