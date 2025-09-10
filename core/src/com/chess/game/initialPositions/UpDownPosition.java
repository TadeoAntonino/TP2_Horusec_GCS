package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;

import java.util.HashMap;

public class UpDownPosition implements InitialPositions{
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
        put(0, TeamEnum.Black);
        put(1, TeamEnum.Black);
        put(2, TeamEnum.Black);
        put(3, TeamEnum.Black);
        put(4, TeamEnum.Black);
        put(5, TeamEnum.Black);
        put(6, TeamEnum.Black);
        put(7, TeamEnum.Black);
        put(8, TeamEnum.Black);
        put(9, TeamEnum.Black);
        put(10, TeamEnum.Black);
        put(11, TeamEnum.Black);
        put(12, TeamEnum.Black);
        put(13, TeamEnum.Black);
        put(14, TeamEnum.Black);
        put(15, TeamEnum.Black);

        put(63, TeamEnum.White);
        put(62, TeamEnum.White);
        put(61, TeamEnum.White);
        put(60, TeamEnum.White);
        put(59, TeamEnum.White);
        put(58, TeamEnum.White);
        put(57, TeamEnum.White);
        put(56, TeamEnum.White);
        put(55, TeamEnum.White);
        put(54, TeamEnum.White);
        put(53, TeamEnum.White);
        put(52, TeamEnum.White);
        put(51, TeamEnum.White);
        put(50, TeamEnum.White);
        put(49, TeamEnum.White);
        put(48, TeamEnum.White);
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
