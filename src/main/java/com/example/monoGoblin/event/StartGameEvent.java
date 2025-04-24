package com.example.monoGoblin.event;

import java.util.UUID;

public class StartGameEvent {
    private final UUID roomUuid;

    public StartGameEvent(UUID roomUuid) {
        this.roomUuid = roomUuid;
    }

    public UUID getRoomUuid() {
        return roomUuid;
    }
}
