package com.chess.game.lab;

public class LabTurn {
    private int turnNumber;
    private LabTurnMove whiteMove;
    private LabTurnMove blackMove;
    public LabTurn(int turnNumber){
        this.turnNumber = turnNumber;
    }

    public void setWhiteMove( LabTurnMove whiteMove){
        this.whiteMove = whiteMove;
    }
    public void setBlackMove( LabTurnMove blackMove){
        this.blackMove = blackMove;
    }
}
