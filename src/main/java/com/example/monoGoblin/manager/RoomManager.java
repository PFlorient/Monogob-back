package com.example.monoGoblin.manager;

import com.example.monoGoblin.model.Room;
import com.example.monoGoblin.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomManager {

    @Autowired
    private RoomRepository roomRepository;

    private Room findRoomByUuid(UUID uuid) {
        return roomRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Room not found"));
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room getRoom(UUID roomUuid) {
        return this.findRoomByUuid(roomUuid);
    }

    public Room createRoom(UUID uuidUser) {
        Room room = new Room();
        List<UUID> users = new ArrayList<>();
        users.add(uuidUser);
        room.setAdministrator_id(uuidUser);
        room.setUsers(users);
        roomRepository.save(room);
        return room;
    }

    public Room joinRoom(UUID roomUuid, UUID uuidUser) {
        Room room = this.findRoomByUuid(roomUuid);

        List<UUID> users = room.getUsers();
        if (users == null) {
            users = new ArrayList<>();
        }

        users.add(uuidUser);
        room.setUsers(users);
        roomRepository.save(room);

        return room;
    }

    public Room leaveRoom(UUID roomUuid, UUID uuidUser) {

        Room room = this.findRoomByUuid(roomUuid);
        List<UUID> users = room.getUsers();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.remove(uuidUser);
        room.setUsers(users);
        roomRepository.save(room);
        return room;

    }

    public void closeRoom(UUID roomUuid) {
        Room room = this.findRoomByUuid(roomUuid);
        room.setEnd_at(new Date());
        roomRepository.save(room);

    }
}

