package com.chess.game.logic.pieceRules;

import java.util.ArrayList;

public class MovementPath {
    private ArrayList<Integer> moves;
    private MovementType movementType;
    public MovementPath(MovementType movementType){
        this.movementType = movementType;
        moves = new ArrayList<Integer>();
    }
    public void addMovement(int row, int col)
    {
        int index = (row * 8)+col;
        moves.add(index);
    }
    public ArrayList<Integer> getMoves(){
        return moves;
    }

    public void setMoves(ArrayList<Integer> moves){
        this.moves = moves;
    }

    public boolean hasMoves(){
        return !moves.isEmpty();
    }

    public MovementType getMovementType() {
        return movementType;
    }
}
