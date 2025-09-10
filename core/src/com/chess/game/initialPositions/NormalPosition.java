package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;

import java.util.HashMap;

public class NormalPosition implements InitialPositions{
    private HashMap<Integer, PieceTypeEnum> pieces = new HashMap<Integer, PieceTypeEnum>()  {{
        put(0, PieceTypeEnum.Rook);
        put(1, PieceTypeEnum.Knight);
        put(2, PieceTypeEnum.Bishop);
        put(3, PieceTypeEnum.Queen);
        put(4, PieceTypeEnum.King);
        put(5, PieceTypeEnum.Bishop);
        put(6, PieceTypeEnum.Knight);
        put(7, PieceTypeEnum.Rook);
        put(8, PieceTypeEnum.Pawn);
        put(9, PieceTypeEnum.Pawn);
        put(10, PieceTypeEnum.Pawn);
        put(11, PieceTypeEnum.Pawn);
        put(12, PieceTypeEnum.Pawn);
        put(13, PieceTypeEnum.Pawn);
        put(14, PieceTypeEnum.Pawn);
        put(15, PieceTypeEnum.Pawn);

        put(63, PieceTypeEnum.Rook);
        put(62, PieceTypeEnum.Knight);
        put(61, PieceTypeEnum.Bishop);
        put(60, PieceTypeEnum.King);
        put(59, PieceTypeEnum.Queen);
        put(58, PieceTypeEnum.Bishop);
        put(57, PieceTypeEnum.Knight);
        put(56, PieceTypeEnum.Rook);
        put(55, PieceTypeEnum.Pawn);
        put(54, PieceTypeEnum.Pawn);
        put(53, PieceTypeEnum.Pawn);
        put(52, PieceTypeEnum.Pawn);
        put(51, PieceTypeEnum.Pawn);
        put(50, PieceTypeEnum.Pawn);
        put(49, PieceTypeEnum.Pawn);
        put(48, PieceTypeEnum.Pawn);
    }};

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
