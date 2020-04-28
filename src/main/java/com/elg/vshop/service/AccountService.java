package com.elg.vshop.service;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.service.security.jwt.dto.RegistratingUserDTO;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    Account findAccountByUserId();

    void saveAccount(RegistratingUserDTO account) throws AccountNotFoundException;

    String getCurrentUserEmail();

    void updateAccount(Account account);

}
