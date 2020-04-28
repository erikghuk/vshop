package com.elg.vshop.dao;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(value = "delete from vshop_schema.utilisateur u where u.id=?1", nativeQuery = true)
    void deleteById(int id);

    User findByUserName(String name);
    User findByAccountEmail(String email);
}
