package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;

public class Movement {

    private MovementType movementType;
    private int cols = 0;
    private int rows = 0;

    public Movement(MovementType movementType, TeamEnum team, int cols, int rows){
        this.movementType = movementType;
        this.rows = rows * team.attackDirection;
        this.cols = cols * team.attackDirection;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public MovementPath getPossibles (int position){
        int rowIndexPosition = position/8;
        int columnIndexPosition = position%8;
        switch (movementType){
            case CONTINUES:
            case CAPTURE:
            case DOUBLE_PAWN_PUSH:
            case SINGLE_PAWN_PUSH:
            case CASTLING:
            case PROMOTE:
            case EN_PASSANT:
            {return getPossiblesContinues(rowIndexPosition, columnIndexPosition);}
            case JUMP:
            {return getPossiblesJump(rowIndexPosition, columnIndexPosition);}
        }

        return null;
    }

    private MovementPath getPossiblesContinues(int rowIndexPosition, int colIndexPosition){
        if(rows == 0 && cols == 0) return null;
        if(rows != 0 && cols == 0) return getRowPossiblesContinues(rowIndexPosition, colIndexPosition);
        if(rows == 0) return getColPossiblesContinues(rowIndexPosition, colIndexPosition);
        return getDiagonalPossiblesContinues(rowIndexPosition, colIndexPosition);
    }

    private MovementPath getRowPossiblesContinues(int rowIndexPosition, int colIndexPosition){
        MovementPath res =  new MovementPath(movementType);
        int absIndex = Math.abs(rows);
        int sign = rows/absIndex;
        for(int i = 1; i <= absIndex; i++) {
            int possibleRowMovementIndex = (sign * i) + rowIndexPosition;
            if(possibleRowMovementIndex >= 0 && possibleRowMovementIndex <= 7){
                res.addMovement(possibleRowMovementIndex, colIndexPosition);
            }
            else{
                break;
            }
        }

        return res;
    }

    private MovementPath getColPossiblesContinues(int rowIndexPosition, int colIndexPosition){
        MovementPath res =  new MovementPath(movementType);
        int absIndex = Math.abs(cols);
        int sign = cols/absIndex;
        for(int i = 1; i <= absIndex; i++) {
            int possibleMovementIndex = (sign * i) + colIndexPosition;
            if(possibleMovementIndex >= 0 && possibleMovementIndex <= 7){
                res.addMovement(rowIndexPosition, possibleMovementIndex);
            }
            else{
                break;
            }
        }
        return res;

    }

    private MovementPath getDiagonalPossiblesContinues(int rowIndexPosition, int colIndexPosition){
        MovementPath res =  new MovementPath(movementType);
        if(Math.abs(rows) != Math.abs(cols)) return null;
        int absRowIndex = Math.abs(rows);
        int rowSign = rows/absRowIndex;
        int absColIndex = Math.abs(cols);
        int colSign = cols/absColIndex;

        for(int i = 1; i <= absRowIndex; i++) {
            int possibleRowMovementIndex = (rowSign * i) + rowIndexPosition;
            int possibleColMovementIndex = (colSign * i) + colIndexPosition;
            if (possibleRowMovementIndex >= 0 && possibleRowMovementIndex <= 7
                && possibleColMovementIndex >= 0 && possibleColMovementIndex <= 7) {
                res.addMovement(possibleRowMovementIndex, possibleColMovementIndex);
            } else {
                break;
            }
        }

        return res;
    }

    private MovementPath getPossiblesJump(int rowIndexPosition, int colIndexPosition){
        MovementPath res =  new MovementPath(movementType);
        int possibleRowMovementIndex = rows + rowIndexPosition;
        int possibleColMovementIndex = cols + colIndexPosition;
        if (possibleRowMovementIndex >= 0 && possibleRowMovementIndex <= 7
                && possibleColMovementIndex >= 0 && possibleColMovementIndex <= 7) {
            res.addMovement(possibleRowMovementIndex, possibleColMovementIndex);
        }
        return res;
    }

}
