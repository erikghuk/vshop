package com.elg.vshop.dao;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
