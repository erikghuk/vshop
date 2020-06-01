package com.elg.vshop.service.security;

import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentAuthenticatedUser {
    private String email;
    private User user;
    private UserRepository userRepository;

    @Autowired
    public CurrentAuthenticatedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer getUserId() {
        getFromRepo();
        if(user == null) {
            throw new JwtAuthenticationException("Access denied");
        }
        return user.getId();
    }

    private void getFromRepo() {
        email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(email == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        user = userRepository.findByAccountEmail(email);
        if(user == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
    }

    public User getUser() {
        getFromRepo();
        return user;
    }

    public String getEmail() {
        getFromRepo();
        return email;
    }


}
