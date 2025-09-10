package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KingRule extends PieceRule {


    public KingRule(TeamEnum team) {
        super(team);
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, -1, 0));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 1, 0));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 0, 1));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 0, -1));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 1, 1));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, 1, -1));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, -1, 1));
        posibleMovements.add(new Movement(MovementType.CONTINUES, team, -1, -1));
    }

    @Override
    public ArrayList<MovementPath> getMovementPaths(int position) {
        addRemoveCastlingMovement();
        return generateMovementPaths(position);
    }

    private void addRemoveCastlingMovement(){
        List<Movement> castlingMovement = posibleMovements.stream().filter(p-> p.getMovementType() == MovementType.CASTLING).collect(Collectors.toList());
        if (!isMoved) {
            if(castlingMovement.isEmpty()) {
                posibleMovements.add(new Movement(MovementType.CASTLING, team, 2, 0));
                posibleMovements.add(new Movement(MovementType.CASTLING, team, -2, 0));
            }
        }
        else{
            if(castlingMovement.isEmpty()) return;
            for(Movement movement : castlingMovement){
                posibleMovements.remove(movement);
            }

        }
    }


}
