package com.example.monoGoblin.event;

import java.util.UUID;

public class TurnEvent {
    private final UUID roomUuid;
    private final UUID userUuid;

    public TurnEvent(UUID roomUuid, UUID userUuid) {
        this.roomUuid = roomUuid;
        this.userUuid = userUuid;
    }


    public UUID getRoomUuid() {
        return roomUuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }


}
