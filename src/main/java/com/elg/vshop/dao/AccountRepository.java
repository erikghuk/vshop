package com.elg.vshop.dao;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);

    Account findByUser(User user);

    @Query(value = "select email from vshop_schema.compte u where u.utilisateur_id = ?1", nativeQuery = true)
    String findEmailByUserId(int id);
}
