package com.elg.vshop.dao;

import com.elg.vshop.entity.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUserId(int userId);
}
