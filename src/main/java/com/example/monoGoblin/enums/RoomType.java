package com.example.monoGoblin.enums;

public enum RoomType {
    JOIN("playerJoined"),
    LEAVE("playerLeft"),
    CLOSE("close");

    private final String value;

    private RoomType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
