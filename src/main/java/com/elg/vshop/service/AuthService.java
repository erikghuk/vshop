package com.elg.vshop.service;

import com.elg.vshop.service.security.jwt.dto.AuthRequestDto;
import org.springframework.http.ResponseEntity;

import java.rmi.server.ServerNotActiveException;

public interface AuthService {
    ResponseEntity authenticate(AuthRequestDto authRequestDto) throws Exception;

    void logOut(String token);
}
