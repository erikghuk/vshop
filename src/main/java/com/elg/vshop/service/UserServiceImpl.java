package com.elg.vshop.service;

import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(int id) {
        Optional<User> result = userRepository.findById(id);
        User existingUser = null;
        if(result.isPresent()) {
            existingUser = result.get();
        } else {
            throw new UsernameNotFoundException("Utilisateur n'existe pas");
        }
        return existingUser;
    }
}
