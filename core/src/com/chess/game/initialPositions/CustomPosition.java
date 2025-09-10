package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;

public class CustomPosition implements InitialPositions {

    private TeamEnum initialTeam;
    private HashMap<Integer, PieceTypeEnum> pieceTypes;
    private HashMap<Integer, TeamEnum> teams;
    private HashMap<Integer, Boolean> isMoved;
    private HashMap<Integer, Piece> pieces;
    public CustomPosition(TeamEnum initialTeam){
        this.initialTeam = initialTeam;
        this.pieceTypes = new HashMap<>();
        this.teams = new HashMap<>();
        this.isMoved = new HashMap<>();
        this.pieces = new HashMap<>();
    }

    public void addPiece(int position, PieceTypeEnum piecetype, TeamEnum team, boolean isMoved){
        if(pieceTypes.containsKey(position)){
            throw new KeyAlreadyExistsException();
        }
        pieceTypes.put(position, piecetype);
        teams.put(position, team);
        this.isMoved.put(position, isMoved);
    }
    @Override
    public Piece getByPosition(int position) {
        Piece piece = pieces.get(position);
        if(piece != null){
            return piece;
        }
        PieceTypeEnum pieceEnum = pieceTypes.get(position);
        if( pieceEnum == null){
            return null;
        }
        TeamEnum teamEnum = teams.get(position);
        boolean isMoved = this.isMoved.get(position);
        piece = new Piece(teamEnum, pieceEnum);
        if(isMoved){
            piece.setMoved();
        }
        pieces.put(position, piece);
        return piece;
    }

    @Override
    public TeamEnum getInitialTeam() {
        return initialTeam;
    }
}
