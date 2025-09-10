package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PawnRule extends PieceRule{
    public PawnRule(TeamEnum team) {
        super(team);
        posibleMovements.add(new Movement(MovementType.SINGLE_PAWN_PUSH, team, 0, 1));
        posibleMovements.add(new Movement(MovementType.CAPTURE, team, 1, 1));
        posibleMovements.add(new Movement(MovementType.CAPTURE, team, -1, 1));

    }

    @Override
    public ArrayList<MovementPath> getMovementPaths(int position) {
        addRemoveDoublePawnPushMovement(position);
        addRemoveEnPassantMovement(position);
        return generateMovementPaths(position);
    }

    private void addRemoveDoublePawnPushMovement(int positionIndex){
        Optional<Movement> doublePawnPushMovementOptional = posibleMovements.stream().filter(p-> p.getMovementType() == MovementType.DOUBLE_PAWN_PUSH).findFirst();
        if (((team == TeamEnum.White && (positionIndex / 8) == 1)
                || (team == TeamEnum.Black && (positionIndex / 8) == 6))) {
            if(!doublePawnPushMovementOptional.isPresent()) {
                posibleMovements.add(new Movement(MovementType.DOUBLE_PAWN_PUSH, team, 0, 2));
            }
        }
        else{
            if(!doublePawnPushMovementOptional.isPresent()) return;
            posibleMovements.remove(doublePawnPushMovementOptional.get());
        }
    }

    private void addRemoveEnPassantMovement(int positionIndex){
        List<Movement> enPassantMovement = posibleMovements.stream().filter(p-> p.getMovementType() == MovementType.EN_PASSANT).collect(Collectors.toList());
        if (((team == TeamEnum.White && (positionIndex / 8) == 4)
                || (team == TeamEnum.Black && (positionIndex / 8) == 3))) {
            if(enPassantMovement.isEmpty()) {
                posibleMovements.add(new Movement(MovementType.EN_PASSANT, team, 1, 1));
                posibleMovements.add(new Movement(MovementType.EN_PASSANT, team, -1, 1));
            }
        }
        else{
            if(enPassantMovement.isEmpty()) return;
            for(Movement movement : enPassantMovement){
                posibleMovements.remove(movement);
            }

        }
    }
}
