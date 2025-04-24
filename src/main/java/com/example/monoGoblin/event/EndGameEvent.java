package com.example.monoGoblin.event;

import java.util.UUID;

public class EndGameEvent {
    private final UUID roomUuid;

    public EndGameEvent(UUID roomUuid) {
        this.roomUuid = roomUuid;
    }

    public UUID getRoomUuid() {
        return roomUuid;
    }
}
