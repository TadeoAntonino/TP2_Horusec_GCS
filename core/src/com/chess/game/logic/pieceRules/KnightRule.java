package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;

import java.util.ArrayList;

public class KnightRule extends PieceRule {
    public KnightRule(TeamEnum team) {
        super(team);
        posibleMovements.add(new Movement(MovementType.JUMP, team, -1, -2));
        posibleMovements.add(new Movement(MovementType.JUMP, team, -1, 2));
        posibleMovements.add(new Movement(MovementType.JUMP, team, -2, -1));
        posibleMovements.add(new Movement(MovementType.JUMP, team, -2, 1));
        posibleMovements.add(new Movement(MovementType.JUMP, team, 1, -2));
        posibleMovements.add(new Movement(MovementType.JUMP, team, 1, 2));
        posibleMovements.add(new Movement(MovementType.JUMP, team, 2, -1));
        posibleMovements.add(new Movement(MovementType.JUMP, team, 2, 1));
    }

    @Override
    public ArrayList<MovementPath> getMovementPaths(int position) {
        return generateMovementPaths(position);
    }
}
