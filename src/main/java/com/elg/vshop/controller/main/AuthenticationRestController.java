package com.elg.vshop.controller.main;

import com.elg.vshop.service.AccountService;
import com.elg.vshop.service.security.jwt.dto.AuthRequestDto;
import com.elg.vshop.service.AuthService;
import com.elg.vshop.service.security.jwt.dto.MessageClassForJson;
import com.elg.vshop.service.security.jwt.dto.RegistratingUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthenticationRestController {
    private AuthService authService;
    private AccountService accountService;

    @Autowired
    public AuthenticationRestController(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @PostMapping("/reg")
    public MessageClassForJson addAccount(@Valid @RequestBody RegistratingUserDTO user) throws AccountNotFoundException {
        accountService.saveAccount(user);
        return new MessageClassForJson("L'inscription est passé avec success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthRequestDto authRequestDto) throws Exception {
        return authService.authenticate(authRequestDto);
    }
}
