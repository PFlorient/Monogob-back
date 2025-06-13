package com.example.monoGoblin.dto;

import java.util.UUID;

public class LightUserDto {
    private final UUID uuid;
    private final String username;

    public LightUserDto(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }
}
