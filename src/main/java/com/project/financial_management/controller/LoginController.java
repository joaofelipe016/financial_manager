package com.project.financial_management.controller;

import com.project.financial_management.dto.LoginRequest;
import com.project.financial_management.dto.LoginResponse;
import com.project.financial_management.repository.UsuarioRepository;
import com.project.financial_management.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        var loginResponse = this.loginService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
