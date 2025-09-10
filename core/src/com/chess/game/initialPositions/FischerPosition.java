package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;

import java.util.*;

public class FischerPosition implements InitialPositions{

    private HashMap<Integer, PieceTypeEnum> create_pieces() {
        HashMap<Integer, PieceTypeEnum> pieces = new HashMap<Integer, PieceTypeEnum>(16);

        Random rng = new Random();
        //Para garantizar cada alfil termine en una casilla de color distinto
        //Seleccionamos una posicion par para uno, impar para el otro.
        int bishop_white_pos = rng.nextInt(3)*2;
        int bishop_black_pos = (rng.nextInt(3)*2)+1;

        //Ordenamos el resto de las piezas de manera aleatoria
        //Hay tres torres en esta lista: la segunda torre (en orden) se reemplaza por el rey
        //De esta manera, garantizamos que el rey siempre se encuentre entre las dos torres
        ArrayList<PieceTypeEnum> remaining_pieces = new ArrayList<>(Arrays.asList(
                PieceTypeEnum.Queen,PieceTypeEnum.Knight,PieceTypeEnum.Knight,
                PieceTypeEnum.Rook,PieceTypeEnum.Rook,PieceTypeEnum.Rook));
        Collections.shuffle(remaining_pieces);
        boolean next_rook_is_king = false;

        pieces.put(bishop_white_pos, PieceTypeEnum.Bishop);
        pieces.put(64-8+bishop_white_pos, PieceTypeEnum.Bishop);
        pieces.put(bishop_black_pos, PieceTypeEnum.Bishop);
        pieces.put(64-8+bishop_black_pos, PieceTypeEnum.Bishop);

        for (int i=0; i< 8; i++){
            if (i == bishop_black_pos || i == bishop_white_pos){
                continue;
            }
            PieceTypeEnum nextPiece = remaining_pieces.get(0);
            if (nextPiece == PieceTypeEnum.Rook){
                if  (next_rook_is_king) {
                    nextPiece = PieceTypeEnum.King;
                }
                next_rook_is_king = !next_rook_is_king;
            }
            pieces.put(i, nextPiece);
            pieces.put(64-8+i, nextPiece);
            remaining_pieces.remove(0);
        }

        for (int i = 8; i < 16; i++){
            pieces.put(i, PieceTypeEnum.Pawn);
            pieces.put(63-i, PieceTypeEnum.Pawn);
        }

        return pieces;
    }

    private HashMap<Integer, PieceTypeEnum> pieces = create_pieces();
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
        PieceTypeEnum pieceEnum = pieces.get(position);
        TeamEnum teamEnum = teams.get(position);
        return pieceEnum != null ? new Piece(teamEnum, pieceEnum) : null;
    }

    @Override
    public TeamEnum getInitialTeam() {
        return TeamEnum.White;
    }
}
