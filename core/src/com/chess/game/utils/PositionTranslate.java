package com.chess.game.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class PositionTranslate {
    private static final ArrayList<Character> columnLetters = new ArrayList<Character>(
            Arrays.asList('a','b', 'c','d','e','f','g','h'));
    public static int getIndexByAlgebraicPosition(String algebraicPosition){
        if(algebraicPosition == null){
            throw new IllegalArgumentException("Position can not be null");
        }
        if(algebraicPosition.length() != 2){
            throw new IllegalArgumentException("The position must be 2 characters");
        }
        int column = columnLetters.indexOf(algebraicPosition.toLowerCase().toCharArray()[0]);
        if(column == -1) {
            throw new IllegalArgumentException("Position not exists");
        }
        int row = Integer.parseInt(algebraicPosition.substring(1));
        return ((row - 1) * 8) + column;
    }
}
