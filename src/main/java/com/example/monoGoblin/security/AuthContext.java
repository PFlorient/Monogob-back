package com.example.monoGoblin.security;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthContext {
    private final JWTService jwtService;

    public AuthContext(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    public UUID getAuthenticatedUser() {
        String token = jwtService.extractToken();
        return jwtService.extractUserUuid(token);
    }
}
