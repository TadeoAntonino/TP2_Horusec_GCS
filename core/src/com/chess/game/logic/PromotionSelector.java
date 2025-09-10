package com.chess.game.logic;

import com.chess.game.logic.contextRules.AntiChessContextRules;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;

import java.util.ArrayList;
import java.util.Optional;

public class PromotionSelector {

    private Board board;
    private ArrayList<Square> squares;
    private Square squarePieceToPromote;
    public PromotionSelector(Board board, Square squarePieceToPromote){
        this.board = board;
        this.squarePieceToPromote = squarePieceToPromote;
        squares = new ArrayList<>();
    }
    public Square getSquareToSelect(int position) {
        Square square = new Square(this, position, getPieceToSelect(position));
        squares.add(square);
        return square;
    }

    public Square getSquarePieceToPromote() {
        return squarePieceToPromote;
    }

    public Square getSquarePieceSelected(int x, int y) {
        Optional<Square> optionalSquare =  squares.stream().filter(p-> p.getInitialXCord() <= x
        && p.getFinalXCord() >= x
        && p.getInitialYCord() <= y
        && p.getFinalYCord() >= y)
                .findFirst();
        return optionalSquare.orElse(null);
    }

    private Piece getPieceToSelect(int position){
        if (getBoard().getContextRules().getClass()== AntiChessContextRules.class){
            switch (position){
                case 0:{
                    return new Piece(this.board.getTeamTurn(), PieceTypeEnum.Queen);
                }
                case 1:{
                    return new  Piece(this.board.getTeamTurn(), PieceTypeEnum.Rook);
                }
                case 2:{
                    return new Piece(this.board.getTeamTurn(), PieceTypeEnum.Knight);
                }
                case 3:{
                    return  new Piece(this.board.getTeamTurn(), PieceTypeEnum.Bishop);
                }
                case 4:{
                    return  new Piece(this.board.getTeamTurn(), PieceTypeEnum.King);
                }
            }
            return null;
        }
        switch (position){
            case 0:{
                return new Piece(this.board.getTeamTurn(), PieceTypeEnum.Queen);
            }
            case 1:{
                return new  Piece(this.board.getTeamTurn(), PieceTypeEnum.Rook);
            }
            case 2:{
                return new Piece(this.board.getTeamTurn(), PieceTypeEnum.Knight);
            }
            case 3:{
                return  new Piece(this.board.getTeamTurn(), PieceTypeEnum.Bishop);
            }
        }
        return null;
    }
    public TeamEnum getTeam(){
        return board.getTeamTurn();
    }
    public Board getBoard(){
        return board;
    }

}
