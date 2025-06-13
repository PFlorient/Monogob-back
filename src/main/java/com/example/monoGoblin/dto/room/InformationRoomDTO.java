package com.example.monoGoblin.dto.room;

import com.example.monoGoblin.dto.LightUserDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InformationRoomDTO {
    private final UUID uuid;
    private final String name;
    private final UUID administrator_uuid;
    private final List<LightUserDto> users;
    private final Date created_at;
    private final Boolean active;

    public InformationRoomDTO(UUID uuid, String name, UUID administrator_uuid, List<LightUserDto> users, Date created_at, Boolean active) {
        this.uuid = uuid;
        this.name = name;
        this.administrator_uuid = administrator_uuid;
        this.users = users;
        this.created_at = created_at;
        this.active = active;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getAdministrator_uuid() {
        return administrator_uuid;
    }

    public List<LightUserDto> getUsers() {
        return users;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Boolean getActive() {
        return active;
    }
}
