package com.elg.vshop.service;

import com.elg.vshop.entity.user.Account;

import java.util.List;

public interface AccountService {

    List<Account> accountsList();

    void save(Account user);

    Account findByEmail(String email);

    void updateAccount(int id, Account account);

    void deleteAccount(Account account);
}
