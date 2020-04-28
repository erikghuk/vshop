package com.elg.vshop.service.security;

import com.elg.vshop.entity.user.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityServiceImpl implements SecurityService {
    private Authentication auth;

    public SecurityServiceImpl() {
        auth = SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String findLoggedInEmail() {
        return auth.getName();
    }
}
