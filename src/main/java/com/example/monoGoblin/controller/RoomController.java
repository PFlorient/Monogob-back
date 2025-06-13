package com.example.monoGoblin.controller;

import com.example.monoGoblin.dto.ApiResponse;
import com.example.monoGoblin.dto.room.CreateRoomDto;
import com.example.monoGoblin.dto.room.InformationRoomDTO;
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
    public ResponseEntity<ApiResponse<InformationRoomDTO>> getRoom(@PathVariable UUID uuid) {
        InformationRoomDTO InformationRoom = roomManager.getRoom(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(InformationRoom, "Room list"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<InformationRoomDTO>> createRoom(@RequestBody CreateRoomDto request) {
        UUID userUuid = authContext.getAuthenticatedUser();
        String roomName = request.getName();
        InformationRoomDTO room = roomManager.createRoom(userUuid, roomName);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(room, "success"));
    }

    @GetMapping("/join/{uuid}")
    public ResponseEntity<ApiResponse<InformationRoomDTO>> joinRoom(@PathVariable UUID uuid) {
        UUID userUuid = authContext.getAuthenticatedUser();

        InformationRoomDTO roomJoined = roomManager.joinRoom(uuid, userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(roomJoined, "Room Joined"));
    }

    @GetMapping("/leave/{uuid}")
    public ResponseEntity<ApiResponse<InformationRoomDTO>> leaveRoom(@PathVariable UUID uuid) {
        UUID userUuid = authContext.getAuthenticatedUser();

        InformationRoomDTO roomLeaved = roomManager.leaveRoom(uuid, userUuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(roomLeaved, "Room Leaved"));
    }

    @GetMapping("/close/{uuid}")
    public ResponseEntity<ApiResponse<?>> closeRoom(@PathVariable UUID uuid) {
        roomManager.closeRoom(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null, "Room closed"));
    }
}
