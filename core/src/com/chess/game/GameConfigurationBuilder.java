package com.chess.game;

import com.chess.game.initialPositions.*;
import com.chess.game.logic.contextRules.*;
import com.chess.game.logic.enums.TeamEnum;

public class GameConfigurationBuilder {

    private InitialPositionType initialPositionType;
    private ContextRuleType contextRuleType;
    private TeamEnum initialTeam;
    public GameConfigurationBuilder(){
        initialPositionType = InitialPositionType.Normal;
        contextRuleType = ContextRuleType.Normal;
    }

    public GameConfigurationBuilder SetInitialPosition(InitialPositionType initialPositionType)
    {
        this.initialPositionType = initialPositionType;
        return this;
    }

    public GameConfigurationBuilder SetContextRules(ContextRuleType contextRuleType)
    {
        this.contextRuleType = contextRuleType;
        return this;
    }
    public GameConfigurationBuilder SetInitialTeam(TeamEnum initialTeam)
    {
        this.initialTeam = initialTeam;
        return this;
    }

    public GameConfiguration Build(){
        InitialPositions initialPosition = GetInitialPosition();
        ContextRules contextRule = GetContextRule();
        return new GameConfiguration(initialPosition, contextRule);
    }

    private InitialPositions GetInitialPosition(){
        switch (initialPositionType){
            case Normal:
                return new NormalPosition();
            case Custom:
                return new CustomPosition(initialTeam != null ? initialTeam : TeamEnum.White);
            case Fischer:
                return new FischerPosition();
            case UpDown:
                return new UpDownPosition();
            case Transcendental:
                return new TrascendentalPosition();
            default:
                throw new IllegalArgumentException();
        }
    }

    private ContextRules GetContextRule(){
        switch (contextRuleType){
            case Normal:
                return new NormalContextRules();
            case Atomic:
                return new AtomicContextRules();
            case AntiChess:
                return new AntiChessContextRules();
            case Random:
                return new RandomPlacementContextRules();
            default:
                throw new IllegalArgumentException();
        }
    }

    public String GetSelectedModeGameName(){
        switch (initialPositionType){
            case Normal: {
                switch (contextRuleType){
                    case Normal:
                        return "Normal";
                    case Atomic:
                        return "Atomico";
                    case AntiChess:
                        return  "Anti-ajedrez";
                    default:
                        return "";
                }
            }
            case Custom:
                return "Partida guardada";
            case Fischer:
                return "Aleat. de Fischer";
            case UpDown:
                return "Arriba-Abajo";
            case Transcendental:
                return "Trascendental";
            default:
                return "";
        }
    }
}
