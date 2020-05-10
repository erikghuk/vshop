package com.elg.vshop.service;

import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.exception.JwtAuthenticationException;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private CurrentAuthenticatedUser authenticatedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CurrentAuthenticatedUser authenticatedUser) {
        this.userRepository = userRepository;
        this.authenticatedUser = authenticatedUser;
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

    @Override
    public void updateUser(User user) {
        User currentUser = authenticatedUser.getUser();
        if(currentUser == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        if(!currentUser.getUserName().equals(user.getUserName())) {
            currentUser.setUserName(user.getUserName());
            userRepository.save(currentUser);
        }
    }

    @Override
    public void deleteUser() {
        User currentUser = authenticatedUser.getUser();
        if(currentUser == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        userRepository.deleteById(currentUser.getId());
    }
}
