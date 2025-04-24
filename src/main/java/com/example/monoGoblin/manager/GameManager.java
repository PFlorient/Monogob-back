package com.example.monoGoblin.manager;

import com.example.monoGoblin.enums.ActionType;
import com.example.monoGoblin.model.GameAction;
import com.example.monoGoblin.model.cards.Card;
import com.example.monoGoblin.publisher.GameActionPublisher;
import com.example.monoGoblin.repository.CardRepository;
import com.example.monoGoblin.repository.GameActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class GameManager {

    private final RoomManager roomManager;
    private final GameActionPublisher gameActionPublisher;
    private final CardRepository cardRepository;
    private final GameActionRepository gameActionRepository;

    @Autowired
    public GameManager(RoomManager roomManager, GameActionPublisher gameActionPublisher, CardRepository cardRepository, GameActionRepository gameActionRepository) {
        this.roomManager = roomManager;
        this.gameActionPublisher = gameActionPublisher;
        this.cardRepository = cardRepository;
        this.gameActionRepository = gameActionRepository;
    }

    public List<GameAction> getAllGameActionsFromRoomUuid(UUID roomUuid) {
        return gameActionRepository.findByRoomUuid(roomUuid);

    }

    public void startGame(UUID roomUuid) {
        roomManager.getRoom(roomUuid);
        gameActionPublisher.startGame(roomUuid);
    }

    public void endGame(UUID roomUuid) {
        roomManager.getRoom(roomUuid);
        gameActionPublisher.endGame(roomUuid);
    }

    public void startTurn(UUID roomUuid, UUID playerUuid) {
        gameActionPublisher.startTurn(roomUuid, playerUuid);
    }

    public Integer rollDice(UUID roomUuid, UUID uuidUser) {
        int diceValue = ThreadLocalRandom.current().nextInt(1, 7);
        gameActionPublisher.rollDice(roomUuid, uuidUser, diceValue);
        return diceValue;
    }

    public Card drawNewCard(UUID roomUuid, UUID uuidUser) {
        List<GameAction> alreadyDrawnCards = this.getAllFilterActionsWithFilter(roomUuid, ActionType.DRAW_CARD);
        List<Integer> alreadyDrawnCardIds = alreadyDrawnCards.stream().map(GameAction::getCardId).toList();
        Card drawedCard = cardRepository.findRandomCardExcluding(alreadyDrawnCardIds);
        gameActionPublisher.drawCard(roomUuid, uuidUser, drawedCard.getId());
        return drawedCard;
    }

    private List<GameAction> getAllFilterActionsWithFilter(UUID roomUuid, UUID uuidUser) {
        List<GameAction> gameActions = this.getAllGameActionsFromRoomUuid(roomUuid);
        return gameActions.stream().filter(gameAction -> gameAction.getPlayerUuid().equals(uuidUser)).collect(Collectors.toList());
    }

    private List<GameAction> getAllFilterActionsWithFilter(UUID roomUuid, ActionType actionType) {
        List<GameAction> gameActions = this.getAllGameActionsFromRoomUuid(roomUuid);
        return gameActions.stream().filter(gameAction -> gameAction.getActionType() == actionType).collect(Collectors.toList());
    }
}
