package com.chess.game.utils;

import com.chess.game.logic.Board;

public interface Observer {
    public void update(Board board);
}
