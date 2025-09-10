package com.chess.game.lab;

import com.chess.game.logic.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LabGame {
    private String moves;
    private Board board;
    private ArrayList<LabTurn> turns;
    public LabGame(Board board, String moves){
        this.board = board;
        this.moves = moves;
        this.turns = new ArrayList<>();
        if(!moves.isEmpty()){
            loadTurnMoves(moves);
        }
    }

    private void loadTurnMoves(String moves) {
        ArrayList<String> movesArray = Arrays.stream(moves.split("\\s")).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < movesArray.size(); i++) {
            String move = movesArray.get(i);
            if(i % 2 == 0){
                if(move.contains(".")){
                    String[] moveParts = move.split("\\.");
                    if(moveParts.length != 2) {
                        throw new IllegalArgumentException(String.format("El movimiento $s de blancas no tiene el formato correcto", i + 1));
                    }
                    int moveNumber = Integer.parseInt(moveParts[0]);
                    LabTurn newTurn = new LabTurn(moveNumber);
                }
            }
            else{

            }
        }
    }
}
