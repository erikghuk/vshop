package com.elg.vshop.dao;

import com.elg.vshop.entity.user.UserDetails;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    List<UserDetails> findByUserId(int userId);

}
