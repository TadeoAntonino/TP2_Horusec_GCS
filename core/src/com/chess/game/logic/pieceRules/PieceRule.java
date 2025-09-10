package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;

import java.util.ArrayList;

public abstract class PieceRule {
    protected TeamEnum team;
    protected boolean isMoved = false;
    protected ArrayList<Movement> posibleMovements ;
    public PieceRule(TeamEnum team){
        posibleMovements = new ArrayList<>();
        this.team = team;
    }
    public abstract ArrayList<MovementPath> getMovementPaths(int position);

    protected ArrayList<MovementPath> generateMovementPaths(int position){
        ArrayList<MovementPath> movementPaths = new ArrayList<>();
        for(Movement possibleMove : posibleMovements){
            MovementPath posibleMovementPath = possibleMove.getPossibles(position);
            if(posibleMovementPath != null && posibleMovementPath.hasMoves()){
                movementPaths.add(posibleMovementPath);
            }
        }
        return movementPaths;
    }
    public void setMoved() {
        isMoved = true;
    }
    public boolean getIsMoved() {
        return isMoved;
    }
}
