package com.chess.game.logic.contextRules;

import com.chess.game.logic.Board;
import com.chess.game.logic.Square;
import com.chess.game.logic.enums.PieceTypeEnum;

public abstract class ContextRules {

    protected Board board;
    public ContextRules(){

    }

    public void setBoard(Board board){
        this.board = board;
    }

    public abstract void generatePossibleMovements();

    public abstract int move(Square startSquare, Square endSquare);
    public abstract int promote(Square square, PieceTypeEnum pieceType);
}
