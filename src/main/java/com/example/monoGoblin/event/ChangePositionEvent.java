package com.example.monoGoblin.event;

import java.util.UUID;

public class ChangePositionEvent {
    private final int position;
    private final UUID PlayerUuid;
    private final UUID roomUuid;

    public ChangePositionEvent(int position, UUID PlayerUuid, UUID roomUuid) {
        this.position = position;
        this.PlayerUuid = PlayerUuid;
        this.roomUuid = roomUuid;
    }

    public int getPosition() {
        return position;
    }

    public UUID getPlayerUuid() {
        return PlayerUuid;
    }

    public UUID getRoomUuid() {
        return roomUuid;
    }
}
