package com.example.monoGoblin.publisher;

import com.example.monoGoblin.event.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameActionPublisher {

    private final ApplicationEventPublisher publisher;

    public GameActionPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void startGame(UUID roomUuid) {
        StartGameEvent startGameEvent = new StartGameEvent(roomUuid);
        publisher.publishEvent(startGameEvent);
    }

    public void endGame(UUID roomUuid) {
        EndGameEvent endGameEvent = new EndGameEvent(roomUuid);
        publisher.publishEvent(endGameEvent);
    }

    public void rollDice(UUID roomUuid, UUID playerUuid, int diceValue) {
        DiceEvent diceEvent = new DiceEvent(roomUuid, playerUuid, diceValue);
        publisher.publishEvent(diceEvent);
    }

    public void changePosition(UUID roomUuid, UUID playerUuid, int position) {
        ChangePositionEvent changePositionEvent = new ChangePositionEvent(position, playerUuid, roomUuid);
        publisher.publishEvent(changePositionEvent);
    }

    public void startTurn(UUID roomUuid, UUID playerUuid) {
        TurnEvent turnEvent = new TurnEvent(roomUuid, playerUuid);
        publisher.publishEvent(turnEvent);
    }

    public void drawCard(UUID roomUuid, UUID playerUuid, int cardId) {
        DrawCardEvent cardTakenEvent = new DrawCardEvent(cardId, playerUuid, roomUuid);
        publisher.publishEvent(cardTakenEvent);
    }
}
