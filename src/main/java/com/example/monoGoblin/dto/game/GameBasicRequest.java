package com.example.monoGoblin.dto.game;

import java.util.UUID;

public class GameBasicRequest {
    private UUID roomUuid;

    public UUID getRoomUuid() {
        return roomUuid;
    }

    public void setRoomUuid(UUID roomUuid) {
        this.roomUuid = roomUuid;
    }
}
