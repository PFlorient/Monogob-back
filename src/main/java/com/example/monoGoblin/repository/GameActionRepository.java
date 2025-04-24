package com.example.monoGoblin.repository;

import com.example.monoGoblin.model.GameAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameActionRepository extends JpaRepository<GameAction, String> {

    List<GameAction> findByRoomUuid(UUID roomUuid);
}
