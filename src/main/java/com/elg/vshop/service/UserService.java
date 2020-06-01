package com.elg.vshop.service;

import com.elg.vshop.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService {
    User getUserById(int id);

    void updateUser(User user);

    void deleteUser();
}
