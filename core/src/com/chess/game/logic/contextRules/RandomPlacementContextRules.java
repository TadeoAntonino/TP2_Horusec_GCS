package com.chess.game.logic.contextRules;

import com.chess.game.logic.Piece;
import com.chess.game.logic.Square;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.SquareStateTypeEnum;
import com.chess.game.logic.pieceRules.MovementPath;
import com.chess.game.logic.pieceRules.MovementType;

import java.util.ArrayList;

public class RandomPlacementContextRules extends NormalContextRules{

    private Integer findRookForCastling(int endPosition, int piecePositionIndex){
        int searchDirection = ((endPosition > piecePositionIndex) ? 1 : -1);
        Integer rookPosition = null;
        for (int rookSearchPosition = piecePositionIndex+searchDirection; (rookSearchPosition >= 0 && rookSearchPosition < 8) || (rookSearchPosition > 55  && rookSearchPosition < 64); rookSearchPosition+=searchDirection){
            Piece possibleRook = board.getPositionSquareByIndex(rookSearchPosition).getPiece();
            if (possibleRook != null){
                rookPosition = rookSearchPosition;
                break;
            }
        }
        return rookPosition;
    }
    protected int castlingMove(Square startSquare, Square endSquare){
        System.out.print("castling from "+startSquare.getPosition()+" to "+endSquare.getPosition());
        Piece selectedPiece = startSquare.getPiece();

        int actualRookPosition = findRookForCastling(endSquare.getPosition(), startSquare.getPosition());
        Square actualRookSquare;
        actualRookSquare = board.getPositionSquareByIndex(actualRookPosition);
        Piece rookPiece = actualRookSquare.getPiece();
        actualRookSquare.setPiece(null);
        startSquare.setPiece(null);

        startSquare.setState(SquareStateTypeEnum.Moved);
        endSquare.setPiece(selectedPiece);
        endSquare.setState(SquareStateTypeEnum.Moved);
        selectedPiece.setMoved();
        Square newRookSquare;

        System.out.print("Rook at" + actualRookPosition);

        if(startSquare.getPosition() < endSquare.getPosition()){
            newRookSquare = board.getPositionSquareByIndex(endSquare.getPosition() - 1);
        }
        else {
            newRookSquare = board.getPositionSquareByIndex(endSquare.getPosition() + 1);
        }

        newRookSquare.setPiece(rookPiece);
        rookPiece.setMoved();
        actualRookSquare.setState(SquareStateTypeEnum.Moved);
        newRookSquare.setState(SquareStateTypeEnum.Moved);
        return 0;

    }
    protected void limitKingCastlingMovements(Piece piece){
        if(piece.getPieceType() != PieceTypeEnum.King){
            return;
        }
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        if(piece.isChecked()){
            for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
                MovementPath possiblePath = possibleMovements.get(pmi);
                if (possiblePath.getMovementType() != MovementType.CASTLING) {
                    continue;
                }
                piece.removePossibleMovementPath(possiblePath);
                pmi--;
            }
            return;
        }
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.CASTLING) {
                continue;
            }
            ArrayList<Integer> possiblePositionMoves = possiblePath.getMoves();
            Integer piecePositionIndex = piece.getSquare().getPosition();

            Integer rookPosition = findRookForCastling(possiblePositionMoves.get(0), piecePositionIndex);
            if (rookPosition == null){
                piece.removePossibleMovementPath(possiblePath);
                pmi--;
                continue;
            }

            Square rookSquare = board.getPositionSquareByIndex(rookPosition);
            Piece rookPiece = rookSquare.getPiece();
            if(rookPiece == null
                    || rookPiece.getTeam() != piece.getTeam()
                    || rookPiece.getPieceType() != PieceTypeEnum.Rook
                    || rookPiece.getIsMoved()) {
                piece.removePossibleMovementPath(possiblePath);
                pmi--;
                continue;
            }

            boolean pathRemoved = false;
            for (Integer possibleIndexPosition : possiblePositionMoves) {
                Square possibleSquareMove = board.getPositionSquareByIndex(possibleIndexPosition);
                if (possibleSquareMove != null && possibleSquareMove.hasPiece() && possibleIndexPosition != rookPosition) {
                    piece.removePossibleMovementPath(possiblePath);
                    pmi--;
                    pathRemoved = true;
                    break;
                }
                if (board.getAllPositionSquaresWithPiece().stream().anyMatch(p -> p.getPiece().getTeam() != piece.getTeam()
                        && p.getPiece().getPossibleMovements().contains(possibleIndexPosition))) {
                    piece.removePossibleMovementPath(possiblePath);
                    pmi--;
                    pathRemoved = true;
                    break;
                }
            }
            if (pathRemoved){
                continue;
            }
            if (possiblePath.getMoves().size() == 1) {
                if (rookPosition != 0 && rookPosition != 7 && rookPosition != 63 && rookPosition != 56) {
                    //remove non-corner swaps
                    System.out.print("Removing " + rookPosition);
                    piece.removePossibleMovementPath(possiblePath);
                    pmi--;
                }
            }
            else {
                possiblePath.getMoves().remove(0);
            }
        }
    }
}
