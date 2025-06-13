package com.example.monoGoblin.dto.auth;

import java.util.UUID;

public record LoginResponse(String token, String username, String email, UUID uuid) {
}

