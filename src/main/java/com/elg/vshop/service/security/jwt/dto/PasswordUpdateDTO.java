package com.elg.vshop.service.security.jwt.dto;

import com.elg.vshop.validator.password.PasswordFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordUpdateDTO {
    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "{name.not.empty}")
    private String oldPassword;

    @Size(min = 6, message = "La taille  doit être min 6 simbol")
    @PasswordFormat
    @NotNull(message = "{name.not.empty}")
    @NotEmpty(message = "{name.not.empty}")
    private String newPassword;

    private String newPasswordConfirm;

    public PasswordUpdateDTO(String oldPassword, String newPassword, String newPasswordConfirm) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
