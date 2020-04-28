package com.elg.vshop.service;

import com.elg.vshop.entity.user.UserInfo;

public interface UserInfoService {
    UserInfo findInfoByUserId();
    void saveAccount(UserInfo userDetails);
    void updateInfo(UserInfo info);
}
