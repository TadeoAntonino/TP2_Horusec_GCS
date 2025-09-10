package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.TeamEnum;

public interface InitialPositions {
    Piece getByPosition(int position);

    TeamEnum getInitialTeam();
}
