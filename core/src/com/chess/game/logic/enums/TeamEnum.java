package com.chess.game.logic.enums;

public enum TeamEnum {
    White(1),
    Black(-1);

    public final int attackDirection;
    TeamEnum(int attackDirection) {
        this.attackDirection = attackDirection;
    }
}
