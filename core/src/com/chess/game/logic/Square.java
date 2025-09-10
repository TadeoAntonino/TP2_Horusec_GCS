package com.chess.game.logic;

import com.chess.game.logic.enums.SquareStateTypeEnum;

public class Square {


    private int position;
    private float initialXCord;
    private float initialYCord;
    private float finalXCord;
    private float finalYCord;
    private Board board;
    private PromotionSelector promotionSelector;
    private Piece piece;
    private SquareStateTypeEnum state;
    public Square(Board board, int position){
        this.board = board;
        this.position = position;
        this.state = SquareStateTypeEnum.Normal;
    }

    public Square(PromotionSelector promotionSelector, int position, Piece piece){
        this.promotionSelector = promotionSelector;
        this.position = position;
        this.piece = piece;
    }

    public int getPosition() {
        return position;
    }

    public int getRowPosition() {
        return position / 8;
    }

    public int getColumnPosition(){
      return position %8;
    };

    public void setPiece(Piece piece){
        this.piece = piece;
        if(this.piece != null)
        {
            this.piece.setBoard(this.board);
            this.piece.setSquare(this);
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece(){
        return piece != null;
    }

    public SquareStateTypeEnum getState() {
        return state;
    }

    public void setState(SquareStateTypeEnum state) {
        this.state = state;
    }

    public void setCord(float initialXCord, float initialYCord, float finalXCord, float finalYCord){
        this.initialXCord = initialXCord;
        this.initialYCord = initialYCord;
        this.finalXCord = finalXCord;
        this.finalYCord = finalYCord;
        if(hasPiece()){
            piece.setSquare(this);
        }
    }

    public float getInitialXCord() {
        return initialXCord;
    }

    public float getInitialYCord() {
        return initialYCord;
    }

    public float getFinalXCord() {
        return finalXCord;
    }

    public float getFinalYCord() {
        return finalYCord;
    }
}
