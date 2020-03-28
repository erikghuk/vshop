package com.elg.vshop.service;

import com.elg.vshop.entity.user.Account;

public interface AccountService {
    void save(Account user);

    Account findByEmail(String email);
}
