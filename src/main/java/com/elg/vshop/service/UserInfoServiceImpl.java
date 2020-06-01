package com.elg.vshop.service;

import com.elg.vshop.dao.UserDetailsRepository;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.entity.user.UserInfo;
import com.elg.vshop.exception.InvalidDataException;
import com.elg.vshop.exception.JwtAuthenticationException;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        User user = authenticatedUser.getUser();
        UserInfo userInfo = user.getUserInfo();
        return userInfo;
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
        User existingUser = authenticatedUser.getUser();

        if(existingUser == null) {
            throw new JwtAuthenticationException("Unauthorised access");
        }
        UserInfo existingInfo = existingUser.getUserInfo();

        if(existingInfo == null && userDetails!= null) {
            userDetails.setUser(existingUser);
            infoRepository.save(userDetails);
        } else if(userDetails != null) {
            if(userDetails.getLastName() == null)
                userDetails.setLastName(" ");
            if(userDetails.getFirstName() == null)
                userDetails.setFirstName(" ");

            if(!userDetails.getFirstName().equals(existingInfo.getFirstName()))
                existingInfo.setFirstName(userDetails.getFirstName());
            if(!userDetails.getLastName().equals(existingInfo.getLastName()))
                existingInfo.setLastName(userDetails.getLastName());
            if(userDetails.getDob() != null && !userDetails.getDob().equals(existingInfo.getDob())) {
                existingInfo.setDob(userDetails.getDob());
            }
            infoRepository.save(existingInfo);
        } else {
            throw new InvalidDataException("Data n'est pas valide");
        }
    }
}
