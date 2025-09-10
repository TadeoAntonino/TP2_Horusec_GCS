package com.chess.game.logic.contextRules;

import com.chess.game.logic.Piece;
import com.chess.game.logic.Square;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.SquareStateTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.logic.pieceRules.MovementPath;
import com.chess.game.logic.pieceRules.MovementType;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class AntiChessContextRules extends ContextRules {

    public AntiChessContextRules() {
        super();
    }
    @Override
    public void generatePossibleMovements() {
        if (board == null) {
            throw new IllegalArgumentException("board");
        }
        clearInPassantPermitted();
        generateWithoutContextMovements();
        limitMovements();
        filterCaptureMoves();
        endGame();
    }
    @Override
    public int move(Square startSquare, Square endSquare) {
        Optional<MovementPath> optionalExecutedMove = startSquare.getPiece().getPossibleMovementPaths().stream().filter(p -> p.getMoves().contains(endSquare.getPosition())).findFirst();
        if (!optionalExecutedMove.isPresent()) {
            return -1;
        }
        MovementPath executedMove = optionalExecutedMove.get();
        int res;
        switch (executedMove.getMovementType()) {
            case DOUBLE_PAWN_PUSH: {
                res = doublePushMove(startSquare, endSquare);
                break;
            }
            case EN_PASSANT: {
                res = enPassantCaptureMove(startSquare, endSquare);
                break;
            }
            default: {
                res = defaultMove(startSquare, endSquare);
                break;
            }
        }

        generatePossibleMovements();
        return res;
    }
    @Override
    public int promote(Square square, PieceTypeEnum pieceType) {
        int res = promoteMove(square, pieceType);
        generatePossibleMovements();
        return res;
    }
    private int defaultMove(Square startSquare, Square endSquare) {
        boolean capture = endSquare.hasPiece() && endSquare.getPiece().getTeam() != board.getTeamTurn();
        boolean isPromote = startSquare.getPiece().getPieceType() == PieceTypeEnum.Pawn
                && ((startSquare.getPiece().isTeam(TeamEnum.White) && endSquare.getRowPosition() == 7
                || startSquare.getPiece().isTeam(TeamEnum.Black) && endSquare.getRowPosition() == 0));
        Piece selectedPiece = startSquare.getPiece();
        startSquare.setPiece(null);
        startSquare.setState(SquareStateTypeEnum.Moved);
        endSquare.setPiece(selectedPiece);
        endSquare.setState(SquareStateTypeEnum.Moved);
        selectedPiece.setMoved();
        return isPromote ? 2 : capture ? 1 : 0;
    }
    private int doublePushMove(Square startSquare, Square endSquare) {
        startSquare.getPiece().setInPassantCapturePermitted();
        return defaultMove(startSquare, endSquare);
    }
    private int enPassantCaptureMove(Square startSquare, Square endSquare) {
        Square doublePushPawnToCaptureSquare = board.getPositionSquareByIndex(endSquare.getPosition() + (startSquare.getPiece().getTeam() == TeamEnum.White ? -8 : 8));
        doublePushPawnToCaptureSquare.setPiece(null);
        defaultMove(startSquare, endSquare);
        return 1;
    }
    private int promoteMove(Square square, PieceTypeEnum pieceType) {
        Piece promotedPiece = new Piece(square.getPiece().getTeam(), pieceType);
        square.setPiece(promotedPiece);
        square.setState(SquareStateTypeEnum.Moved);
        promotedPiece.setMoved();
        return 2;
    }
    private void clearInPassantPermitted() {
        for (Piece piece : board.getAllTeamPositionSquares(board.getTeamTurn() == TeamEnum.White ? TeamEnum.Black : TeamEnum.White).stream().map(Square::getPiece).collect(Collectors.toCollection(ArrayList::new))) {
            piece.clearInPassantPermitted();
        }
    }
    private void generateWithoutContextMovements() {
        for (Square square : board.getAllPositionSquaresWithPiece()) {
            square.getPiece().generatePossibleMovements();
            square.getPiece().cleanDefendedBy();
        }
    }
    private void limitMovements() {

            for (Square square : board.getAllPositionSquaresWithPiece()) {
                Piece piece = square.getPiece();
                if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.CASTLING)) {
                    limitPieceCastlingMovements(piece);
                }
                if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.CONTINUES)) {
                    limitPieceContinuousMovements(piece);
                }
                if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.JUMP)) {
                    limitPieceJumpMovements(piece);
                }
                if (piece.getPieceType() == PieceTypeEnum.Pawn) {
                    if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.SINGLE_PAWN_PUSH)) {
                        limitPieceSinglePawnPushMovements(piece);
                    }
                    if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.DOUBLE_PAWN_PUSH)) {
                        limitPieceDoublePawnPushMovements(piece);
                    }
                    if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.CAPTURE)) {
                        limitPieceCaptureMovements(piece);
                    }
                    if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.EN_PASSANT)) {
                        limitPawnInPassantCaptureMovements(piece);
                    }
                }
            }

    }
    private void limitPieceContinuousMovements(Piece piece) {
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.CONTINUES) {
                continue;
            }
            ArrayList<Integer> possiblePositionMoves = possiblePath.getMoves();
            for (int i = 0; i < possiblePositionMoves.size(); i++) {
                Integer possibleIndexPosition = possiblePositionMoves.get(i);
                Square possibleSquareMove = board.getPositionSquareByIndex(possibleIndexPosition);
                if (possibleSquareMove != null && possibleSquareMove.hasPiece()) {
                    if (possibleSquareMove.getPiece().getTeam() != piece.getTeam()) {
                            for (int j = i + 1; j < possiblePositionMoves.size(); j++) {
                                Integer backThreatenedSquareIndex = possiblePositionMoves.get(j);
                                Square backThreatenedSquare = board.getPositionSquareByIndex(backThreatenedSquareIndex);
                                if (!backThreatenedSquare.hasPiece()) continue;
                                if (backThreatenedSquare.getPiece().getTeam() == piece.getTeam()
                                        || backThreatenedSquare.getPiece().getPieceType() != PieceTypeEnum.King) {
                                    break;
                                }
                            }
                            possiblePath.setMoves(new ArrayList<>(possiblePositionMoves.subList(0, i + 1)));

                    }
                    else {
                        if (piece.getPieceType() != PieceTypeEnum.Pawn) {
                            possibleSquareMove.getPiece().addDefendedBy(piece);
                        }
                        if (i == 0) {
                            piece.removePossibleMovementPath(possiblePath);
                            pmi--;
                        } else {
                            possiblePath.setMoves(new ArrayList<>(possiblePositionMoves.subList(0, i)));
                        }
                    }
                    break;
                }
            }
        }
    }
    private boolean isSquarePieceUnderAttack(Integer squareIndex, TeamEnum team){
        Square squareToValidate= board.getPositionSquareByIndex(squareIndex);
        return squareToValidate.hasPiece() && squareToValidate.getPiece().isTeam(team);
    }
    private void filterCaptureMoves() {
        TeamEnum opponentTeam = board.getTeamTurn();
        TeamEnum currentTeam = (opponentTeam == TeamEnum.White) ? TeamEnum.Black : TeamEnum.White;

        ArrayList<Square> attackerTeamPiecesSquares = board.getAllTeamPositionSquares(currentTeam).stream()
                .filter(p -> !p.getPiece().getPossibleMovements().isEmpty()
                && p.getPiece().getPossibleMovements().stream().anyMatch(q-> isSquarePieceUnderAttack(q, opponentTeam)))
                .collect(Collectors.toCollection(ArrayList::new));

        if(attackerTeamPiecesSquares.isEmpty()){
            return;
        }

        ArrayList<Square>  nonAttackerTeamPiecesSquares= board.getAllTeamPositionSquares(currentTeam).stream()
                .filter(p -> !attackerTeamPiecesSquares.contains(p))
                .collect(Collectors.toCollection(ArrayList::new));
        for (Square square : nonAttackerTeamPiecesSquares) {
            Piece piece = square.getPiece();
            piece.removedAllPossibleMovementPath();
        }

        for(Square attackerPieceSquare: attackerTeamPiecesSquares){
            Piece piece = attackerPieceSquare.getPiece();
            ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
            for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
                MovementPath possiblePath = possibleMovements.get(pmi);
                ArrayList<Integer> attackerPossibleMovementSquareIndexes = possiblePath.getMoves().stream()
                        .filter(p-> isSquarePieceUnderAttack(p, opponentTeam))
                        .collect(Collectors.toCollection(ArrayList::new));
                if(attackerPossibleMovementSquareIndexes.isEmpty()){
                    piece.removePossibleMovementPath(possiblePath);
                    pmi--;
                }
                else{
                    possiblePath.setMoves(new ArrayList<>(attackerPossibleMovementSquareIndexes));
                }
            }
        }


    }
    private void limitPieceCastlingMovements(Piece piece){
        if(piece.getPieceType() != PieceTypeEnum.King){
            return;
        }
        piece.removePossibleMovementPathByType(MovementType.CASTLING);
    }
    private void limitPieceJumpMovements(Piece piece) {
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.JUMP) {
                continue;
            }
            Integer possibleMovementSquareIndex = possiblePath.getMoves().get(0);
            Square possibleSquareMove = board.getPositionSquareByIndex(possibleMovementSquareIndex);
            if (possibleSquareMove == null || !possibleSquareMove.hasPiece()) {
                continue;
            }
            if (possibleSquareMove.getPiece().getTeam() == piece.getTeam()) {
                possibleSquareMove.getPiece().addDefendedBy(piece);
                piece.removePossibleMovementPath(possiblePath);
                pmi--;
            }
        }
    }
    private void limitPieceSinglePawnPushMovements(Piece piece) {
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.SINGLE_PAWN_PUSH) {
                continue;
            }
            Integer possibleMovementSquareIndex = possiblePath.getMoves().get(0);
            Square possibleSquareMove = board.getPositionSquareByIndex(possibleMovementSquareIndex);
            if (possibleSquareMove == null || !possibleSquareMove.hasPiece()) {
                continue;
            }
            piece.removePossibleMovementPath(possiblePath);
            pmi--;
        }
    }
    private void limitPieceDoublePawnPushMovements(Piece piece) {
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.DOUBLE_PAWN_PUSH) {
                continue;
            }
            Integer initialPossibleMovementSquareIndex = possiblePath.getMoves().get(0);
            Integer completePossibleMovementSquareIndex = possiblePath.getMoves().get(1);
            Square initialPossibleSquareMove = board.getPositionSquareByIndex(initialPossibleMovementSquareIndex);
            Square completePossibleSquareMove = board.getPositionSquareByIndex(completePossibleMovementSquareIndex);
            if ((initialPossibleSquareMove == null || !initialPossibleSquareMove.hasPiece())
                    && (completePossibleSquareMove == null || !completePossibleSquareMove.hasPiece())) {
                continue;
            }
            piece.removePossibleMovementPath(possiblePath);
            pmi--;
        }
    }
    private void limitPieceCaptureMovements(Piece pawnPiece) {
        ArrayList<MovementPath> possibleMovements = pawnPiece.getPossibleMovementPaths();
        for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.CAPTURE) {
                continue;
            }
            Integer possibleMovementSquareIndex = possiblePath.getMoves().get(0);
            Square possibleSquareMove = board.getPositionSquareByIndex(possibleMovementSquareIndex);
            if (possibleSquareMove != null && possibleSquareMove.hasPiece() && possibleSquareMove.getPiece().getTeam() == pawnPiece.getTeam()) {
                possibleSquareMove.getPiece().addDefendedBy(pawnPiece);
            }
            if (possibleSquareMove == null || !possibleSquareMove.hasPiece() || possibleSquareMove.getPiece().getTeam() == pawnPiece.getTeam()) {
                pawnPiece.removePossibleMovementPath(possiblePath);
                pmi--;
            }
        }
    }
    private void limitPawnInPassantCaptureMovements(Piece pawnPiece) {
        ArrayList<MovementPath> possibleMovements = pawnPiece.getPossibleMovementPaths();
        for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.EN_PASSANT) {
                continue;
            }
            Integer possibleMovementSquareIndex = possiblePath.getMoves().get(0);
            Square possibleSquareWithPawnInDoublePush = board.getPositionSquareByIndex(possibleMovementSquareIndex + (pawnPiece.getTeam() == TeamEnum.White ? -8 : 8));
            if (possibleSquareWithPawnInDoublePush == null
                    || !possibleSquareWithPawnInDoublePush.hasPiece()
                    || possibleSquareWithPawnInDoublePush.getPiece().getTeam() == pawnPiece.getTeam()
                    || possibleSquareWithPawnInDoublePush.getPiece().getPieceType() != PieceTypeEnum.Pawn
                    || !possibleSquareWithPawnInDoublePush.getPiece().getInPassantCapturePermitted()) {
                pawnPiece.removePossibleMovementPath(possiblePath);
                pmi--;
            }
        }
    }
    private void endGame(){
        TeamEnum currentTeam = (board.getTeamTurn() == TeamEnum.White) ? TeamEnum.Black : TeamEnum.White;
        ArrayList<Square> piecesThatCanMove = board.getAllTeamPositionSquares(currentTeam).stream()
                .filter(p -> !p.getPiece().getPossibleMovements().isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
        if(piecesThatCanMove.isEmpty()){
            board.setWinner();
        }
    }
}



