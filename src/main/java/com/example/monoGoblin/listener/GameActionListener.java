package com.example.monoGoblin.listener;

import com.example.monoGoblin.enums.ActionType;
import com.example.monoGoblin.event.*;
import com.example.monoGoblin.model.GameAction;
import com.example.monoGoblin.repository.GameActionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameActionListener {

    private final GameActionRepository gameActionRepository;


    public GameActionListener(GameActionRepository repository) {
        this.gameActionRepository = repository;
    }

    @EventListener
    public void onStartGameEvent(StartGameEvent event) {
        GameAction gameAction = new GameAction();
        gameAction.setRoomUuid(event.getRoomUuid());
        gameAction.setActionType(ActionType.GAME_START);
        gameActionRepository.save(gameAction);
    }

    @EventListener
    public void onEndGameEvent(EndGameEvent event) {
        GameAction gameAction = new GameAction();
        gameAction.setRoomUuid(event.getRoomUuid());
        gameAction.setActionType(ActionType.GAME_END);
        gameActionRepository.save(gameAction);
    }

    @EventListener
    public void onPlayerTurnEvent(TurnEvent turnEvent) {
        GameAction gameAction = new GameAction();
        gameAction.setRoomUuid(turnEvent.getRoomUuid());
        gameAction.setPlayerUuid(turnEvent.getUserUuid());
        gameAction.setActionType(ActionType.TURN_STARTED);
        gameActionRepository.save(gameAction);
    }

    @EventListener
    public void onDiceEvent(DiceEvent diceEvent) {
        GameAction gameAction = new GameAction();
        gameAction.setRoomUuid(diceEvent.getRoomUuid());
        gameAction.setPlayerUuid(diceEvent.getPlayerUuid());
        gameAction.setDiceValue(diceEvent.getDiceValue());
        gameAction.setActionType(ActionType.ROLL_DICE);
        gameActionRepository.save(gameAction);
    }

    @EventListener
    public void onChangePositionEvent(ChangePositionEvent changePositionEvent) {
        GameAction gameAction = new GameAction();
        gameAction.setRoomUuid(changePositionEvent.getRoomUuid());
        gameAction.setPlayerUuid(changePositionEvent.getPlayerUuid());
        gameAction.setNewPosition(changePositionEvent.getPosition());
        gameAction.setActionType(ActionType.MOVE);
        gameActionRepository.save(gameAction);
    }

    @EventListener
    public void onDrawCardEvent(DrawCardEvent drawCardEvent) {
        GameAction gameAction = new GameAction();
        gameAction.setRoomUuid(drawCardEvent.getRoomUuid());
        gameAction.setPlayerUuid(drawCardEvent.getPlayerUuid());
        gameAction.setCardId(drawCardEvent.getId());
        gameAction.setActionType(ActionType.DRAW_CARD);
        gameActionRepository.save(gameAction);
    }
}
