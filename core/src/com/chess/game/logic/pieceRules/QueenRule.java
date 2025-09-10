package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;
import java.util.ArrayList;
public class QueenRule extends PieceRule {
    public QueenRule(TeamEnum team) {
        super(team);
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, -7, 0));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 7, 0));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 0, 7));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 0, -7));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 7, 7));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 7, -7));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, -7, 7));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, -7, -7));
    }

    @Override
    public ArrayList<MovementPath> getMovementPaths(int position) {
        return generateMovementPaths(position);
    }
}
