package com.example.monoGoblin.manager;

import com.example.monoGoblin.dto.LightUserDto;
import com.example.monoGoblin.dto.room.InformationRoomDTO;
import com.example.monoGoblin.enums.RoomType;
import com.example.monoGoblin.model.Room;
import com.example.monoGoblin.model.UserModel;
import com.example.monoGoblin.repository.RoomRepository;
import com.example.monoGoblin.repository.UserRepository;
import com.example.monoGoblin.service.SocketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class RoomManager {


    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SocketService socketService;

    public RoomManager(RoomRepository roomRepository, UserRepository userRepository, SocketService socketService) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.socketService = socketService;
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public InformationRoomDTO getRoom(UUID roomUuid) {
        Room room = findRoomByUuid(roomUuid);
        return this.convertRoomToInformationRoomDTO(room);
    }

    public InformationRoomDTO createRoom(UUID uuidUser, String name) {
        UserModel user = findUserByUuid(uuidUser);

        Room room = new Room();

        room.setName(name);
        room.setAdministrator_uuid(uuidUser);
        room.setUsers(new ArrayList<>(List.of(user)));

        roomRepository.save(room);

        return convertRoomToInformationRoomDTO(room);

    }

    public InformationRoomDTO joinRoom(UUID roomUuid, UUID uuidUser) {
        Room room = findRoomByUuid(roomUuid);
        UserModel user = findUserByUuid(uuidUser);
        int maxSizeRoom = 4;
        List<UserModel> users = getOrInitListUsers(room);


        if (users.contains(user)) {
            return convertRoomToInformationRoomDTO(room);
        }
        if (users.size() >= maxSizeRoom) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La salle est pleine (maximum " + maxSizeRoom + " utilisateurs)"
            );
        }

        users.add(user);
        room.setUsers(users);
        roomRepository.save(room);
        List<LightUserDto> userDtoList = users.stream().map(user2 -> new LightUserDto(user2.getId(), user2.getUsername()))
                .toList();
        this.socketService.sendToRoom(roomUuid.toString(), RoomType.JOIN.getValue(), userDtoList);
        return convertRoomToInformationRoomDTO(room);
    }

    public InformationRoomDTO leaveRoom(UUID roomUuid, UUID uuidUser) {
        Room room = findRoomByUuid(roomUuid);
        UserModel user = findUserByUuid(uuidUser);

        List<UserModel> users = getOrInitListUsers(room);
        users.remove(user);

        room.setUsers(users);
        roomRepository.save(room);
        List<LightUserDto> userDtoList = users.stream().map(user2 -> new LightUserDto(user2.getId(), user2.getUsername()))
                .toList();
        this.socketService.sendToRoom(roomUuid.toString(), RoomType.LEAVE.getValue(), userDtoList);
        return convertRoomToInformationRoomDTO(room);
    }

    public void closeRoom(UUID roomUuid) {
        Room room = findRoomByUuid(roomUuid);
        room.setEnd_at(new Date());
        roomRepository.save(room);
    }

    private Room findRoomByUuid(UUID uuid) {
        return roomRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Room not found"));
    }

    private UserModel findUserByUuid(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    private List<UserModel> getOrInitListUsers(Room room) {
        List<UserModel> users = room.getUsers();
        return (users != null) ? users : new ArrayList<>();
    }


    private InformationRoomDTO convertRoomToInformationRoomDTO(Room room) {
        List<LightUserDto> userDtoList = room.getUsers().stream()
                .map(user -> new LightUserDto(user.getId(), user.getUsername()))
                .toList();

        return new InformationRoomDTO(room.getUuid(), room.getName(), room.getAdministrator_uuid(), userDtoList, room.getCreated_at(), room.getActive());
    }
}

