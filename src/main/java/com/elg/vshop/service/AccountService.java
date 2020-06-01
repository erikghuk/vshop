package com.elg.vshop.service;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.service.security.jwt.dto.PasswordUpdateDTO;
import com.elg.vshop.service.security.jwt.dto.RegistratingUserDTO;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    Account findAccountByUserId();

    void saveAccount(RegistratingUserDTO account) throws AccountNotFoundException;

    String getCurrentUserEmail();

    ResponseEntity updateAccount(Account account) throws AccountNotFoundException;

    boolean checkPasword(String password);

    ResponseEntity updatePass(PasswordUpdateDTO passwordUpdateDTO);
}
