package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;

import java.util.*;

public class TrascendentalPosition implements InitialPositions{

    PieceTypeEnum pieceType;
    HashMap<Integer, PieceTypeEnum> piecesPositions = new HashMap<>();
    private HashMap<Integer, PieceTypeEnum> createPieces (){

        HashMap<Integer,PieceTypeEnum> pieces = new HashMap<>();
        Random random = new Random();
        PieceTypeEnum[] pieceCaseWhite = {PieceTypeEnum.Rook,PieceTypeEnum.Rook,PieceTypeEnum.Knight,PieceTypeEnum.Knight,PieceTypeEnum.Queen,PieceTypeEnum.King};
        PieceTypeEnum[] pieceCaseBlack = {PieceTypeEnum.Rook,PieceTypeEnum.Rook,PieceTypeEnum.Knight,PieceTypeEnum.Knight,PieceTypeEnum.Queen,PieceTypeEnum.King};
        List<PieceTypeEnum> piecesCaseW = Arrays.asList(pieceCaseWhite);
        List<PieceTypeEnum> piecesCaseB = Arrays.asList(pieceCaseBlack);// Guardo las piezas blancas y negras en dos listas, excepto los alfiles
        Collections.shuffle(piecesCaseB);
        Collections.shuffle(piecesCaseW); // Mezclo las listas que contienen las piezas
        Integer whiteBishopPosition = random.nextInt(3)*2;
        Integer whiteBishopPosition2 = (random.nextInt(3)*2)+1;
        Integer blackBishopPosition = random.nextInt(3)*2;
        Integer blackBishopPosition2 = (random.nextInt(3)*2)+1;//Defino las posiciones de los alfiles para que quede cada uno en una casilla de distinto color

        // Posiciones de los peones
        for(int i= 8; i<=15; i++){
            pieces.put(i,PieceTypeEnum.Pawn);
            pieces.put(63-i,PieceTypeEnum.Pawn);
        }

        // Posiciones de los alfiles
        for(int j = 0; j<=7; j++){

            if(j==whiteBishopPosition){
                pieces.put(whiteBishopPosition,PieceTypeEnum.Bishop);
            }if(j==whiteBishopPosition2){
                pieces.put(whiteBishopPosition2,PieceTypeEnum.Bishop);
            }if((63-j)== 63-blackBishopPosition){
                pieces.put(63-blackBishopPosition,PieceTypeEnum.Bishop);
            }if((63-j)==63-blackBishopPosition2){
                pieces.put(63-blackBishopPosition2,PieceTypeEnum.Bishop);
            }

        }
        // Posiciones de las piezas negras
        for(int k=0; k<piecesCaseB.size(); k++){

            if(!pieces.containsKey(63-k)){
                pieces.put(63-k, piecesCaseB.get(k));
            }else if(!pieces.containsKey(63-k-1)){
                pieces.put(63-k-1, piecesCaseB.get(k));}
            else{
                pieces.put(63-k-2, piecesCaseB.get(k));
            }
        }
        // Posiciones de las piezas blancas
        for(int l=0; l<piecesCaseW.size(); l++){

            if(!pieces.containsKey(l)){
                pieces.put(l, piecesCaseW.get(l));
            }else if(!pieces.containsKey(l+1)){
                pieces.put(l+1, piecesCaseW.get(l));}
            else{
                pieces.put(l+2, piecesCaseW.get(l));
            }
        }

        return pieces;

    };

    private HashMap<Integer, PieceTypeEnum> getHashPieces(){

        if(piecesPositions.isEmpty()) {
            piecesPositions = createPieces();
        }
        return piecesPositions;
    };


    private HashMap<Integer, TeamEnum> teams = new HashMap<Integer, TeamEnum>()  {{
        put(0, TeamEnum.White);
        put(1, TeamEnum.White);
        put(2, TeamEnum.White);
        put(3, TeamEnum.White);
        put(4, TeamEnum.White);
        put(5, TeamEnum.White);
        put(6, TeamEnum.White);
        put(7, TeamEnum.White);
        put(8, TeamEnum.White);
        put(9, TeamEnum.White);
        put(10, TeamEnum.White);
        put(11, TeamEnum.White);
        put(12, TeamEnum.White);
        put(13, TeamEnum.White);
        put(14, TeamEnum.White);
        put(15, TeamEnum.White);

        put(63, TeamEnum.Black);
        put(62, TeamEnum.Black);
        put(61, TeamEnum.Black);
        put(60, TeamEnum.Black);
        put(59, TeamEnum.Black);
        put(58, TeamEnum.Black);
        put(57, TeamEnum.Black);
        put(56, TeamEnum.Black);
        put(55, TeamEnum.Black);
        put(54, TeamEnum.Black);
        put(53, TeamEnum.Black);
        put(52, TeamEnum.Black);
        put(51, TeamEnum.Black);
        put(50, TeamEnum.Black);
        put(49, TeamEnum.Black);
        put(48, TeamEnum.Black);
    }};
    @Override
    public Piece getByPosition(int position) {
        PieceTypeEnum pieceEnum = getHashPieces().get(position);
        TeamEnum teamEnum = teams.get(position);
        return pieceEnum != null ? new Piece(teamEnum, pieceEnum) : null;
    }

    @Override
    public TeamEnum getInitialTeam() {
        return TeamEnum.White;
    }
}
