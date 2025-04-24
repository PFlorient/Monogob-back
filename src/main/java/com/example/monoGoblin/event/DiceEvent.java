package com.example.monoGoblin.event;

import java.util.UUID;

public class DiceEvent {
    private final int diceValue;
    private final UUID playerUuid;
    private final UUID roomUuid;

    public DiceEvent(UUID roomUuid, UUID playerUuid, int diceValue) {
        this.diceValue = diceValue;
        this.playerUuid = playerUuid;
        this.roomUuid = roomUuid;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public UUID getRoomUuid() {
        return roomUuid;
    }
}
