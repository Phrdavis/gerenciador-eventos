package com.gevents.gerenciador_eventos.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.gevents.gerenciador_eventos.Service.JwtService;
import com.gevents.gerenciador_eventos.Service.UserDetailsServiceImpl;

import java.util.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtService.generateToken(authentication);

        var user = userDetailsService.loadUserEntityByEmail(request.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "name", user.getNomeCompleto(),
            "tipo", user.getTipo()
        ));

        return response;
    }
}

@Getter @Setter @NoArgsConstructor
class AuthRequest {
    private String email;
    private String password;
}