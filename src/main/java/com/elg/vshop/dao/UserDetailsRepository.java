package com.elg.vshop.dao;

import com.elg.vshop.entity.user.User;
import com.elg.vshop.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserDetailsRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUserId(int userId);
    UserInfo findByUser(User user);


}
