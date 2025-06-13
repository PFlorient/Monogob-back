package com.example.monoGoblin.service;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Service;

@Service
public class SocketService {
    private final SocketIOServer socketIOServer;

    public SocketService(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    public void sendToRoom(String roomUuid, String event, Object data) {
        System.out.println("sendToRoom");
        System.out.println("roomUuid: " + roomUuid);
        socketIOServer.getRoomOperations(roomUuid).sendEvent(event, data);
    }
}
