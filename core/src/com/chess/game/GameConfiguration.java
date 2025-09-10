package com.chess.game;

import com.chess.game.initialPositions.InitialPositions;
import com.chess.game.logic.contextRules.ContextRules;

public class GameConfiguration {
    private InitialPositions initialPosition;
    private ContextRules contextRule;

    public GameConfiguration(InitialPositions initialPosition, ContextRules contextRule){
        this.initialPosition = initialPosition;
        this.contextRule = contextRule;
    }

    public InitialPositions getInitialPosition() {
        return initialPosition;
    }

    public ContextRules getContextRule() {
        return contextRule;
    }
}
