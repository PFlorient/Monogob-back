package com.example.monoGoblin.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;


@org.springframework.context.annotation.Configuration
public class SocketServerConfig {

    @Value("${socket.host}")
    private String SOCKET_HOST;

    @Value("${socket.port}")
    private int SOCKET_PORT;

    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(SOCKET_HOST);
        config.setPort(SOCKET_PORT);
        server = new SocketIOServer(config);

        registerEventListeners(server);

        return this.server;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        server.start();
    }

    @PreDestroy
    public void stop() {
        server.stop();
    }

    private void registerEventListeners(SocketIOServer server) {

        server.addEventListener("joinRoom", String.class, (client, roomUuid, ackSender) -> {
            System.out.println("🔗 Client joined room: " + roomUuid);
            client.joinRoom(roomUuid);
        });
        
        server.addConnectListener(client -> {
            System.out.println("✅ Client connected: " + client.getSessionId());
        });

        server.addDisconnectListener(client -> {
            System.out.println("❌ Client disconnected: " + client.getSessionId());
        });
    }
}
