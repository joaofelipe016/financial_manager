package com.project.financial_management.service;

import com.project.financial_management.dto.LoginRequest;
import com.project.financial_management.dto.LoginResponse;
import com.project.financial_management.entity.Role;
import com.project.financial_management.entity.Usuario;
import com.project.financial_management.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(JwtEncoder jwtEncoder, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        var usuario = this.usuarioRepository.findByScUsuario(loginRequest.scUsuario());

        if(usuario.isEmpty() || isLoginCorrect(loginRequest, usuario.get().getScSenha())) {
            throw new BadCredentialsException("Usuario ou senha é inválido");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = usuario.get().getRole()
                .stream()
                .map(Role::getNmRole)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(usuario.get().getIdUsuario().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
    }

    private boolean isLoginCorrect(LoginRequest loginRequest, String password) throws Exception {
        return this.passwordEncoder.matches(loginRequest.scSenha(), password);
    }
}
