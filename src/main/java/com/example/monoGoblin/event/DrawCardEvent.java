package com.example.monoGoblin.event;

import java.util.UUID;

public class DrawCardEvent {
    private final int id;
    private final UUID playerUuid;
    private final UUID roomUuid;

    public DrawCardEvent(int id, UUID playerUuid, UUID roomUuid) {
        this.id = id;
        this.playerUuid = playerUuid;
        this.roomUuid = roomUuid;
    }

    public int getId() {
        return id;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public UUID getRoomUuid() {
        return roomUuid;
    }
}
