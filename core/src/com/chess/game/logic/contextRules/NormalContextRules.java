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

public class NormalContextRules extends ContextRules {
    public NormalContextRules(){
        super();
    }
    @Override
    public void generatePossibleMovements() {
        if(board == null){
            throw new IllegalArgumentException("board");
        }
        clearInPassantPermitted();
        generateWithoutContextMovements();
        limitMovements();
        validateInCheck();
        limitKingsMovements();
        validateCheckmate();
    }
    @Override
    public int move(Square startSquare, Square endSquare) {
        Optional<MovementPath> optionalExecutedMove = startSquare.getPiece().getPossibleMovementPaths().stream().filter(p-> p.getMoves().contains(endSquare.getPosition())).findFirst();
        if(!optionalExecutedMove.isPresent()){
            return -1;
        }
        MovementPath executedMove = optionalExecutedMove.get();
        int res;
        switch (executedMove.getMovementType()){
            case CASTLING:{
                res = castlingMove(startSquare, endSquare);
                break;
            }
            case DOUBLE_PAWN_PUSH: {
                res = doublePushMove(startSquare, endSquare);
                break;
            }
            case EN_PASSANT: {
                res = enPassantCaptureMove(startSquare, endSquare);
                break;
            }
            default:{
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

    private int defaultMove(Square startSquare, Square endSquare){
        boolean capture = endSquare.hasPiece() && endSquare.getPiece().getTeam() != board.getTeamTurn();
        boolean isPromote = startSquare.getPiece().getPieceType() == PieceTypeEnum.Pawn
                    && ((startSquare.getPiece().isTeam(TeamEnum.White) && endSquare.getRowPosition() == 7
                    || startSquare.getPiece().isTeam(TeamEnum.Black) && endSquare.getRowPosition() == 0 ));
        Piece selectedPiece = startSquare.getPiece();
        startSquare.setPiece(null);
        startSquare.setState(SquareStateTypeEnum.Moved);
        endSquare.setPiece(selectedPiece);
        endSquare.setState(SquareStateTypeEnum.Moved);
        selectedPiece.setMoved();
        return isPromote ? 2 : capture ? 1 : 0;
    }

    protected int castlingMove(Square startSquare, Square endSquare){
        Piece selectedPiece = startSquare.getPiece();
        startSquare.setPiece(null);
        startSquare.setState(SquareStateTypeEnum.Moved);
        endSquare.setPiece(selectedPiece);
        endSquare.setState(SquareStateTypeEnum.Moved);
        selectedPiece.setMoved();
        Square actualRookSquare;
        Square newRookSquare;
        if(startSquare.getPosition() < endSquare.getPosition()){
            actualRookSquare = board.getPositionSquareByIndex(startSquare.getPosition() + 3);
            newRookSquare = board.getPositionSquareByIndex(startSquare.getPosition() + 1);
        }
        else {
            actualRookSquare = board.getPositionSquareByIndex(startSquare.getPosition() - 4);
            newRookSquare = board.getPositionSquareByIndex(startSquare.getPosition() - 1);
        }
        Piece rookPiece = actualRookSquare.getPiece();
        newRookSquare.setPiece(rookPiece);
        actualRookSquare.setPiece(null);
        rookPiece.setMoved();
        actualRookSquare.setState(SquareStateTypeEnum.Moved);
        newRookSquare.setState(SquareStateTypeEnum.Moved);
        return 0;
    }
    private int doublePushMove(Square startSquare, Square endSquare){
        startSquare.getPiece().setInPassantCapturePermitted();
        return defaultMove(startSquare, endSquare);
    }
    private int enPassantCaptureMove(Square startSquare, Square endSquare){
        Square doublePushPawnToCaptureSquare = board.getPositionSquareByIndex(endSquare.getPosition() + (startSquare.getPiece().getTeam() == TeamEnum.White ? - 8 : 8));
        doublePushPawnToCaptureSquare.setPiece(null);
        defaultMove(startSquare, endSquare);
        return 1;
    }
    private int promoteMove(Square square, PieceTypeEnum pieceType){
        Piece promotedPiece = new Piece(square.getPiece().getTeam(),pieceType);
        square.setPiece(promotedPiece);
        square.setState(SquareStateTypeEnum.Moved);
        promotedPiece.setMoved();
        return 2;
    }
    private void clearInPassantPermitted(){
        for(Piece piece : board.getAllTeamPositionSquares(board.getTeamTurn() == TeamEnum.White ? TeamEnum.Black : TeamEnum.White).stream().map(Square::getPiece).collect(Collectors.toCollection(ArrayList::new))) {
            piece.clearInPassantPermitted();
        }
    }
    private void generateWithoutContextMovements(){
        for(Square square : board.getAllPositionSquaresWithPiece()){
            square.getPiece().generatePossibleMovements();
            square.getPiece().cleanDefendedBy();
        }
    }
    private void limitMovements(){
        for(Square square : board.getAllPositionSquaresWithPiece()) {
            Piece piece = square.getPiece();
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
                    limitPawnCaptureMovements(piece);
                }
                if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.EN_PASSANT)) {
                    limitPawnInPassantCaptureMovements(piece);
                }
            }
        }
    }
    private void validateInCheck(){
        ArrayList<Square> kingsSquares = board.getAllPositionSquares().stream()
                .filter(p -> p.hasPiece()
                        && p.getPiece().getPieceType() == PieceTypeEnum.King)
                .collect(Collectors.toCollection(ArrayList::new));
        for(Square kingSquare: kingsSquares){
            kingSquare.getPiece().setChecked(
                    board.getAllPositionSquares().stream().anyMatch(p-> p.hasPiece()
                            && p.getPiece().getTeam() != kingSquare.getPiece().getTeam()
                            && p.getPiece().getPossibleMovements().contains(kingSquare.getPosition())));
        }
    }
    private void limitKingsMovements(){
        for(Square square : board.getAllPositionSquares()) {
            if (!square.hasPiece() || square.getPiece().getPieceType() != PieceTypeEnum.King) {
                continue;
            }
            Piece piece = square.getPiece();
            if (piece.getPossibleMovementPaths().stream().anyMatch(p -> p.getMovementType() == MovementType.CASTLING)) {
                limitKingCastlingMovements(piece);
            }
            limitKingMovementsByAttackedSquares(piece);
        }
    }
    private void limitPieceContinuousMovements(Piece piece){
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++){
            MovementPath possiblePath = possibleMovements.get(pmi);
            if(possiblePath.getMovementType() != MovementType.CONTINUES) {
                continue;
            }

            ArrayList<Integer> possiblePositionMoves = possiblePath.getMoves();
            for(int i = 0; i < possiblePositionMoves.size(); i++){
                Integer possibleIndexPosition = possiblePositionMoves.get(i);
                Square possibleSquareMove = board.getPositionSquareByIndex(possibleIndexPosition);
                if(possibleSquareMove != null && possibleSquareMove.hasPiece()) {
                    if (possibleSquareMove.getPiece().getTeam() == piece.getTeam()) {
                        if(piece.getPieceType() != PieceTypeEnum.Pawn){
                            possibleSquareMove.getPiece().addDefendedBy(piece);
                        }
                        if (i == 0) {
                            piece.removePossibleMovementPath(possiblePath);
                            pmi--;
                        } else {
                            possiblePath.setMoves(new ArrayList<>(possiblePositionMoves.subList(0, i)));
                        }
                    }
                    else {
                        Piece threatenedPiece = possibleSquareMove.getPiece();
                        boolean isPinned = false;
                        for(int j = i + 1; j < possiblePositionMoves.size(); j++){
                            Integer backThreatenedSquareIndex = possiblePositionMoves.get(j);
                            Square backThreatenedSquare = board.getPositionSquareByIndex(backThreatenedSquareIndex);
                            if(!backThreatenedSquare.hasPiece()) continue;
                            if(backThreatenedSquare.getPiece().getTeam() == piece.getTeam()
                                    ||  backThreatenedSquare.getPiece().getPieceType() != PieceTypeEnum.King){
                                break;
                            }
                            if(backThreatenedSquare.getPiece().getTeam() != piece.getTeam()
                                    &&  backThreatenedSquare.getPiece().getPieceType() == PieceTypeEnum.King){
                                isPinned = true;
                                break;
                            }
                        }
                        if(isPinned){
                            ArrayList<MovementPath> pinnedPieceMovementPaths = threatenedPiece.getPossibleMovementPaths();
                            for(int t = 0; t < pinnedPieceMovementPaths.size(); t++){
                                MovementPath pinnedPiecePossibleMovementPath = pinnedPieceMovementPaths.get(t);
                                if(pinnedPiecePossibleMovementPath.getMoves().stream().noneMatch(p -> possiblePath.getMoves().contains(p) ||  piece.getSquare().getPosition() == p)){
                                    threatenedPiece.removePossibleMovementPath(pinnedPiecePossibleMovementPath);
                                    t--;
                                }
                            }
                        }

                        possiblePath.setMoves(new ArrayList<>(possiblePositionMoves.subList(0, i + 1)));
                    }
                    break;
                }
            }
        }
    }
    private void limitPieceJumpMovements(Piece piece) {
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
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
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
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
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
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
    private void limitPawnCaptureMovements(Piece pawnPiece) {
        ArrayList<MovementPath> possibleMovements = pawnPiece.getPossibleMovementPaths();
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.CAPTURE) {
                continue;
            }
            Integer possibleMovementSquareIndex = possiblePath.getMoves().get(0);
            Square possibleSquareMove = board.getPositionSquareByIndex(possibleMovementSquareIndex);
            if(possibleSquareMove != null && possibleSquareMove.hasPiece() && possibleSquareMove.getPiece().getTeam() == pawnPiece.getTeam()){
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
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.EN_PASSANT) {
                continue;
            }
            Integer possibleMovementSquareIndex = possiblePath.getMoves().get(0);
            Square possibleSquareWithPawnInDoublePush = board.getPositionSquareByIndex(possibleMovementSquareIndex + (pawnPiece.getTeam() == TeamEnum.White ? - 8 : 8));
            if(possibleSquareWithPawnInDoublePush == null
                || !possibleSquareWithPawnInDoublePush.hasPiece()
                || possibleSquareWithPawnInDoublePush.getPiece().getTeam() == pawnPiece.getTeam()
                || possibleSquareWithPawnInDoublePush.getPiece().getPieceType() != PieceTypeEnum.Pawn
                || !possibleSquareWithPawnInDoublePush.getPiece().getInPassantCapturePermitted()){
                pawnPiece.removePossibleMovementPath(possiblePath);
                pmi--;
            }
        }
    }

    protected void limitKingCastlingMovements(Piece piece) {
        if(piece.getPieceType() != PieceTypeEnum.King){
            return;
        }
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        if(piece.isChecked()){
            piece.removePossibleMovementPathByType(MovementType.CASTLING);
            return;
        }
        for(int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            if (possiblePath.getMovementType() != MovementType.CASTLING) {
                continue;
            }
            ArrayList<Integer> possiblePositionMoves = possiblePath.getMoves();
            Integer piecePositionIndex = piece.getSquare().getPosition();
            int rookIndex = (possiblePositionMoves.stream().allMatch(p-> p > piecePositionIndex) ? 3 : -4) + piecePositionIndex;
            Square rookSquare = board.getPositionSquareByIndex(rookIndex);
            Piece rookPiece = rookSquare.getPiece();
            if(rookPiece == null
                || !rookPiece.isTeam(piece.getTeam())
                || rookPiece.getPieceType() != PieceTypeEnum.Rook
                || rookPiece.getIsMoved()) {
                piece.removePossibleMovementPath(possiblePath);
                pmi--;
                continue;
            }
            for (Integer possibleIndexPosition : possiblePositionMoves) {
                Square possibleSquareMove = board.getPositionSquareByIndex(possibleIndexPosition);
                if (possibleSquareMove != null && possibleSquareMove.hasPiece()) {
                    piece.removePossibleMovementPath(possiblePath);
                    pmi--;
                    break;
                }
                if (possibleSquareMove != null && possibleSquareMove.getPosition() == piece.getSquare().getPosition() - 2) {
                    possibleSquareMove = board.getPositionSquareByIndex(possibleSquareMove.getPosition() - 1);
                    if (possibleSquareMove != null && possibleSquareMove.hasPiece()) {
                        piece.removePossibleMovementPath(possiblePath);
                        pmi--;
                        break;
                    }
                }
                if (board.getAllPositionSquaresWithPiece().stream().map(Square::getPiece)
                        .anyMatch(p -> !p.isTeam(piece.getTeam())
                            && p.isAttackingTo(possibleIndexPosition))) {
                    piece.removePossibleMovementPath(possiblePath);
                    pmi--;
                    break;
                }
            }
        }
    }
    private void limitKingMovementsByAttackedSquares(Piece piece) {
        if (piece.getPieceType() != PieceTypeEnum.King) {
            return;
        }
        ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
        for (int pmi = 0; pmi < possibleMovements.size(); pmi++) {
            MovementPath possiblePath = possibleMovements.get(pmi);
            ArrayList<Integer> possiblePositionMoves = possiblePath.getMoves();
            if (board.getAllPositionSquaresWithPiece().stream()
                    .map(Square::getPiece)
                    .anyMatch(p ->
                    !p.isTeam(piece.getTeam())
                     && possiblePositionMoves.stream().anyMatch(p::isAttackingTo))) {
                piece.removePossibleMovementPath(possiblePath);
                pmi--;
                continue;
            }
            ArrayList<Square> possiblePositionSquares = board.getAllPositionSquares().stream().filter(p -> possiblePositionMoves.contains(p.getPosition()))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (possiblePositionSquares.stream().anyMatch(p -> p.hasPiece()
                    && !p.getPiece().isTeam(piece.getTeam())
                    && p.getPiece().isDefended())) {
                piece.removePossibleMovementPath(possiblePath);
                pmi--;
            }
        }
        if (!piece.isChecked()) {
            return;
        }
        ArrayList<Square> attackingPieceSquares = board.getAllPositionSquares().stream().filter(p -> p.hasPiece()
                        && p.getPiece().getTeam() != piece.getTeam()
                        && p.getPiece().getPieceType() != PieceTypeEnum.Pawn
                        && p.getPiece().getPossibleMovements().contains(piece.getSquare().getPosition()))
                .collect(Collectors.toCollection(ArrayList::new));
        for (Square attackingPieceSquare : attackingPieceSquares) {
            Optional<MovementPath> optionalAttackingPath = attackingPieceSquare.getPiece().getPossibleMovementPaths().stream()
                    .filter(p -> p.getMoves().contains(piece.getSquare().getPosition()))
                    .findFirst();
            if (!optionalAttackingPath.isPresent()) {
                continue;
            }
            Optional<MovementPath> optionalOriginalAttackingPath = attackingPieceSquare.getPiece().getOriginalPossibleMovementPaths().stream()
                    .filter(p -> p.getMoves().contains(piece.getSquare().getPosition())
                            && p.getMovementType() == MovementType.CONTINUES)
                    .findFirst();
            if (!optionalOriginalAttackingPath.isPresent()) {
                continue;
            }
            ArrayList<Integer> originalAttackingMoves = optionalOriginalAttackingPath.get().getMoves();
            ArrayList<MovementPath> movementPathsToRemove = piece.getPossibleMovementPaths().stream()
                    .filter(p -> p.getMoves().stream().anyMatch(originalAttackingMoves::contains))
                    .collect(Collectors.toCollection(ArrayList::new));
            for (MovementPath movementPathToRemove : movementPathsToRemove) {
                piece.removePossibleMovementPath(movementPathToRemove);
            }
        }

    }
    private void validateCheckmate(){
        ArrayList<Square> kingsSquares = board.getAllPositionSquaresWithPiece().stream()
                .filter(p -> p.getPiece().getPieceType() == PieceTypeEnum.King)
                .collect(Collectors.toCollection(ArrayList::new));
        for(Square kingSquare: kingsSquares){
            Piece kingPiece = kingSquare.getPiece();
            if(!kingPiece.isChecked()){
                ArrayList<Piece> otherPieceSquares = board.getAllTeamPositionSquares(kingPiece.getTeam()).stream()
                        .map(Square::getPiece)
                        .filter(p-> p != kingPiece && !p.getPossibleMovements().isEmpty())
                        .collect(Collectors.toCollection(ArrayList::new));
                if(kingPiece.getTeam() != board.getTeamTurn()
                        && kingPiece.getPossibleMovements().isEmpty()
                        && otherPieceSquares.isEmpty()){
                    board.setDraw();
                    return;
                }
                continue;
            }
            ArrayList<Square> attackingPieces = board.getAllPositionSquaresWithPiece().stream().filter(p->
                            !p.getPiece().isTeam(kingSquare.getPiece().getTeam())
                            && p.getPiece().isAttackingTo(kingSquare.getPosition()))
                    .collect(Collectors.toCollection(ArrayList::new));

            if(attackingPieces.size() > 1 && kingPiece.getPossibleMovements().isEmpty()) {
                board.setWinner();
                return;
            }
            int kingPiecePositionIndex = kingSquare.getPosition();
            Square attackingSquare = attackingPieces.get(0);
            Piece attackingPiece = attackingSquare.getPiece();
            int attackingPieceIndexPosition = attackingSquare.getPosition();
            Optional<MovementPath> optionalCheckMovementAttack = attackingPiece.getPossibleMovementPaths().stream().filter(p-> p.getMoves().contains(kingPiecePositionIndex)).findFirst();
            MovementPath checkMovementAttack = optionalCheckMovementAttack.get();
            filterMovesThatDoNotDefendKing(attackingPieceIndexPosition, checkMovementAttack, kingPiece.getTeam());
            if(kingPiece.getPossibleMovements().isEmpty()
            && board.getAllTeamPositionSquares(kingPiece.getTeam()).stream()
                    .allMatch(p-> p.getPiece().getPossibleMovements().isEmpty())){
                board.setWinner();
                return;
            }
        }
    }
    private void filterMovesThatDoNotDefendKing(int attackingPieceIndex, MovementPath attackingPath, TeamEnum kingTeam){
        ArrayList<Square> teamPiecesSquares = board.getAllTeamPositionSquares(kingTeam).stream()
                .filter(p -> p.getPiece().getPieceType() != PieceTypeEnum.King
                        && !p.getPiece().getPossibleMovements().isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
        for(Square teamPieceSquare: teamPiecesSquares){
            Piece piece = teamPieceSquare.getPiece();
            ArrayList<MovementPath> possibleMovements = piece.getPossibleMovementPaths();
            for(int pmi = 0; pmi < possibleMovements.size(); pmi++){
                MovementPath possibleMovementsPath = possibleMovements.get(pmi);
                ArrayList<Integer> possibleMovementIndexes = possibleMovementsPath.getMoves();
                if(possibleMovementIndexes.stream().allMatch(p-> p != attackingPieceIndex
                    && !attackingPath.getMoves().contains(p))){
                    piece.removePossibleMovementPath(possibleMovementsPath);
                    pmi--;
                }
                else{
                    possibleMovementsPath.setMoves(new ArrayList<>(possibleMovementIndexes.stream()
                            .filter(p-> p == attackingPieceIndex || attackingPath.getMoves().contains(p))
                            .collect(Collectors.toCollection(ArrayList::new))));
                }
            }
        }
    }
}
