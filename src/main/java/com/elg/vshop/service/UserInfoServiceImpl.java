package com.elg.vshop.service;

import com.elg.vshop.dao.UserDetailsRepository;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.entity.user.UserInfo;
import com.elg.vshop.exception.InvalidDataException;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private UserDetailsRepository infoRepository;
    private UserService userService;
    private CurrentAuthenticatedUser authenticatedUser;

    @Autowired
    public UserInfoServiceImpl(UserDetailsRepository infoRepository, UserService userService, CurrentAuthenticatedUser authenticatedUser) {
        this.infoRepository = infoRepository;
        this.userService = userService;
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public UserInfo findInfoByUserId() {
        Integer userId = authenticatedUser.getUserId();
        User user = userService.getUserById(userId);
        return infoRepository.findByUser(user);
    }

    @Override
    public void saveAccount(UserInfo userDetails) {
        Integer userId = authenticatedUser.getUserId();
        User user = userService.getUserById(userId);
        if (userDetails == null) {
            throw new InvalidDataException("Data n'est pas valide");
        }
        userDetails.setUser(user);
        infoRepository.save(userDetails);

    }

    @Override
    public void updateInfo(UserInfo userDetails) {
        Integer userId = authenticatedUser.getUserId();
        User existingUser = userService.getUserById(userId);
        UserInfo existingInfo = infoRepository.findByUser(existingUser);
        if(existingInfo == null && userDetails!= null) {
            userDetails.setUser(existingUser);
            infoRepository.save(userDetails);
        } else if(userDetails != null) {
            if(!userDetails.getFirstName().equals(existingInfo.getFirstName()))
                existingInfo.setFirstName(userDetails.getFirstName());
            if(!userDetails.getLastName().equals(existingInfo.getLastName()))
                existingInfo.setLastName(userDetails.getLastName());
            if(!userDetails.getDob().equals(existingInfo.getDob()))
                existingInfo.setDob(userDetails.getDob());
            infoRepository.save(existingInfo);
        } else {
            throw new InvalidDataException("Data n'est pas valide");
        }
    }
}
