package com.chess.game.logic;

import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.logic.pieceRules.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Piece {
    private TeamEnum teamEnum;
    private PieceTypeEnum pieceType;
    private Board board;
    private Square square;
    private PieceRule pieceRule;
    private ArrayList<MovementPath> possibleMovements;
    private ArrayList<Piece> defendedBy;
    private boolean isChecked;
    private boolean inPassantPermitted;

    public Piece(TeamEnum teamEnum, PieceTypeEnum type){
        this.teamEnum = teamEnum;
        this.isChecked = false;
        this.defendedBy = new ArrayList<>();
        setPieceType(type);
    }
    private PieceRule getPieceRule() {
        switch (pieceType){
            case Pawn:{ return new PawnRule(teamEnum); }
            case King:{ return new KingRule(teamEnum); }
            case Queen:{ return new QueenRule(teamEnum); }
            case Bishop:{ return new BishopRule(teamEnum); }
            case Knight:{ return new KnightRule(teamEnum); }
            case Rook:{ return new RookRule(teamEnum); }
        }
        return null;
    }
    public TeamEnum getTeam() {
        return teamEnum;
    }
    public PieceTypeEnum getPieceType() {
        return pieceType;
    }
    private void setPieceType(PieceTypeEnum pieceType){
        this.pieceType = pieceType;
        this.pieceRule = getPieceRule();
    }
    public void promote(PieceTypeEnum pieceType){
        setPieceType(pieceType);
    }
    public void setSquare(Square square) {
        this.square = square;
    }
    public Square getSquare() {
        return square;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public boolean hasPossibleMovements(){
        return (long) possibleMovements.size() > 0;
    }
    public void generatePossibleMovements(){
        if(pieceRule == null) {
            possibleMovements = new ArrayList<>();
            return;
        }
        possibleMovements =  pieceRule.getMovementPaths(square.getPosition());
    }
    public ArrayList<MovementPath> getPossibleMovementPaths(){
        return possibleMovements;
    }
    public ArrayList<MovementPath> getOriginalPossibleMovementPaths(){
        return pieceRule.getMovementPaths(square.getPosition());
    }
    public void removePossibleMovementPath(MovementPath possibleMovementPath){
        possibleMovements.remove(possibleMovementPath);
    }
    public void removedAllPossibleMovementPath(){
        possibleMovements= new ArrayList<>();
    }
    public void removePossibleMovementPathByType(MovementType possibleMovementPathType){
        possibleMovements = possibleMovements.stream().filter(p-> p.getMovementType() != possibleMovementPathType)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Integer> getPossibleMovements(){
        if(possibleMovements == null) return new ArrayList<>();
        ArrayList<Integer> movementPaths = new ArrayList<>();
        for(MovementPath movPath : possibleMovements)
        {
            movementPaths.addAll(movPath.getMoves());
        }
        return movementPaths;
    }
    public void setMoved(){
        pieceRule.setMoved();
    }
    public boolean getIsMoved() {
        return pieceRule.getIsMoved();
    }

    public boolean isChecked() {
        if(pieceType != PieceTypeEnum.King) {
            return false;
        }
        return isChecked;
    }

    public void setChecked(boolean checked) {
        if(pieceType != PieceTypeEnum.King){
            isChecked = false;
        }else {
            //Gdx.app.debug(String.format("%s %s is %schecked", teamEnum, pieceType, checked ? "" : "not "), "");
            isChecked = checked;
        }
    }

    public void cleanDefendedBy(){
        this.defendedBy = new ArrayList<>();
    }

    public void addDefendedBy(Piece piece){
        this.defendedBy.add(piece);
    }

    public boolean isDefended(){
        return !defendedBy.isEmpty();
    }

    public boolean isTeam(TeamEnum teamEnum){
        return this.teamEnum == teamEnum;
    }

    public boolean isAttackingTo (int positionIndex){
        switch (pieceType){
            case Pawn: {
                return getOriginalPossibleMovementPaths().stream().anyMatch(q->
                        q.getMovementType() == MovementType.CAPTURE
                                && q.getMoves().contains(positionIndex));
            }
            case King:{
                return getOriginalPossibleMovementPaths().stream().anyMatch(q->
                        q.getMovementType() == MovementType.CONTINUES
                                && q.getMoves().contains(positionIndex));
            }
            default:{
                return getPossibleMovements().contains(positionIndex);
            }
        }
    }

    public void setInPassantCapturePermitted() {
        if(pieceType != PieceTypeEnum.Pawn) {
            return;
        }
        inPassantPermitted = true;
    }

    public boolean getInPassantCapturePermitted() {
        if(pieceType != PieceTypeEnum.Pawn) {
            return false;
        }
        return inPassantPermitted;
    }

    public void clearInPassantPermitted(){
        inPassantPermitted = false;
    }
}
