package com.example.monoGoblin.controller;

import com.example.monoGoblin.dto.ApiResponse;
import com.example.monoGoblin.dto.game.GameBasicRequest;
import com.example.monoGoblin.manager.GameManager;
import com.example.monoGoblin.model.GameAction;
import com.example.monoGoblin.model.cards.Card;
import com.example.monoGoblin.security.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/game")
@RestController
public class GameController {
    private final GameManager gameManager;
    private final AuthContext authContext;

    @Autowired
    public GameController(GameManager gameManager, AuthContext authContext) {
        this.gameManager = gameManager;
        this.authContext = authContext;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GameAction>>> getGame(@RequestParam GameBasicRequest request) {
        UUID roomUuid = request.getRoomUuid();

        List<GameAction> actions = gameManager.getAllGameActionsFromRoomUuid(roomUuid);
        return ResponseEntity.ok(ApiResponse.success(actions, "List of actions"));
    }

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<?>> startGame(@RequestBody GameBasicRequest request) {
        UUID roomUuid = request.getRoomUuid();

        gameManager.startGame(roomUuid);
        return ResponseEntity.ok(ApiResponse.success(null, "Game started"));
    }

    @PostMapping("/end")
    public ResponseEntity<ApiResponse<?>> endGame(@RequestBody GameBasicRequest request) {
        UUID roomUuid = request.getRoomUuid();
        gameManager.endGame(roomUuid);
        return ResponseEntity.ok(ApiResponse.success(null, "Game ended"));
    }

    @PostMapping("/roll")
    public ResponseEntity<ApiResponse<Integer>> rollDice(@RequestBody GameBasicRequest request) {
        UUID roomUuid = request.getRoomUuid();
        UUID userUuid = authContext.getAuthenticatedUser();

        Integer diceValue = gameManager.rollDice(roomUuid, userUuid);
        return ResponseEntity.ok(ApiResponse.success(diceValue, "Dice: " + diceValue));
    }

    @PostMapping("/draw")
    public ResponseEntity<ApiResponse<Card>> drawCard(@RequestBody GameBasicRequest request) {
        UUID roomUuid = request.getRoomUuid();
        UUID userUuid = authContext.getAuthenticatedUser();

        Card drawnCard = gameManager.drawNewCard(roomUuid, userUuid);
        return ResponseEntity.ok(ApiResponse.success(drawnCard, "Card drawn"));
    }

    @PostMapping("/changeTurn")
    public ResponseEntity<ApiResponse<?>> changeTurn(@RequestBody GameBasicRequest request) {
        UUID roomUuid = request.getRoomUuid();
        UUID userUuid = authContext.getAuthenticatedUser();

        gameManager.startTurn(roomUuid, userUuid);
        return ResponseEntity.ok(ApiResponse.success(null, "Turn started"));
    }


}
