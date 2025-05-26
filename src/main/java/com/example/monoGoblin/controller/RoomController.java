package com.example.monoGoblin.controller;

import com.example.monoGoblin.dto.ApiResponse;
import com.example.monoGoblin.dto.room.CreateRoomDto;
import com.example.monoGoblin.manager.RoomManager;
import com.example.monoGoblin.model.Room;
import com.example.monoGoblin.security.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/room")
@RestController
public class RoomController {

    private final RoomManager roomManager;
    private final AuthContext authContext;

    @Autowired
    public RoomController(RoomManager roomManager, AuthContext authContext) {
        this.roomManager = roomManager;
        this.authContext = authContext;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Room>>> getRooms() {
        List<Room> rooms = roomManager.getRooms();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(rooms, "success"));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<Room>> getRoom(@PathVariable UUID uuid) {
        Room room = roomManager.getRoom(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(room, "Room list"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Room>> createRoom(@RequestBody CreateRoomDto request) {
        UUID userUuid = authContext.getAuthenticatedUser();
        String roomName = request.getName();
        Room room = roomManager.createRoom(userUuid, roomName);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(room, "success"));
    }

    @GetMapping("/{uuid}/join")
    public ResponseEntity<ApiResponse<Room>> joinRoom(@PathVariable UUID uuid) {
        UUID userUuid = authContext.getAuthenticatedUser();

        Room roomJoined = roomManager.joinRoom(uuid, userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(roomJoined, "Room Joined"));
    }

    @GetMapping("/{uuid}/leave")
    public ResponseEntity<ApiResponse<Room>> leaveRoom(@PathVariable UUID uuid) {
        UUID userUuid = authContext.getAuthenticatedUser();

        Room roomLeaved = roomManager.leaveRoom(uuid, userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(roomLeaved, "Room Leaved"));
    }

    @GetMapping("/{uuid}/close")
    public ResponseEntity<ApiResponse<?>> closeRoom(@PathVariable UUID uuid) {
        roomManager.closeRoom(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null, "Room closed"));
    }
}
