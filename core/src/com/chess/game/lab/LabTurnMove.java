package com.chess.game.lab;

import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.logic.pieceRules.MovementType;

public class LabTurnMove {
    private TeamEnum team;
    private PieceTypeEnum pieceToMove;
    private int initialPositionIndex;
    private int endPositionIndex;
    private PieceTypeEnum pieceToCapture;
    private MovementType movementType;
}
