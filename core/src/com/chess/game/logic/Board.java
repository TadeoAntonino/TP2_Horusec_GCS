package com.chess.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Null;
import com.chess.game.GameConfiguration;
import com.chess.game.initialPositions.InitialPositions;
import com.chess.game.initialPositions.NormalPosition;
import com.chess.game.lab.LabGame;
import com.chess.game.logic.contextRules.AntiChessContextRules;
import com.chess.game.logic.contextRules.ContextRules;
import com.chess.game.logic.contextRules.NormalContextRules;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.SquareStateTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.utils.Observer;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board {
    private int actualTurn;
    private ArrayList<Square> positions;
    private InitialPositions initialPosition;
    private ContextRules contextRules;
    private TeamEnum teamTurn;
    private Piece selectedPiece;
    private PromotionSelector promotionSelector;
    private TeamEnum teamWin;
    private boolean isInitialGame;
    private boolean gameOver;
    private LabGame labGame;

    private ArrayList<Observer> boardObserver;

    public Board(GameConfiguration gameConfiguration){
        this.actualTurn = 0;
        this.gameOver = false;
        this.teamWin = null;
        this.promotionSelector = null;
        this.initialPosition = gameConfiguration.getInitialPosition();
        this.contextRules = gameConfiguration.getContextRule();
        this.contextRules.setBoard(this);
        positions =  new ArrayList<>();
        for(int i = 0; i < 64; i++){
            Square square = new Square(this, i);
            square.setPiece(initialPosition.getByPosition(i));
            positions.add(square);
        }
        teamTurn = initialPosition.getInitialTeam();
        this.contextRules.generatePossibleMovements();
        this.boardObserver = new ArrayList<>();
        this.isInitialGame = true;
        this.labGame = null;
        updateObservers();
    }

    public void setGame(String moves){
        if( !this.isInitialGame ){
            throw new IllegalArgumentException("No se pude cargar una partida cuando el juego ya está iniciado");
        }
        if(initialPosition.getClass() != NormalPosition.class){
            throw new IllegalArgumentException("No se pude cargar una partida cuando la posición inicial no es la Normal");
        }
        if(contextRules.getClass() != NormalContextRules.class){
            throw new IllegalArgumentException("No se pude cargar una partida cuando las reglas de contexto son distintas a las Normales");
        }
        this.labGame =  new LabGame(this, moves);
    }

    public ArrayList<Square> getAllPositionSquares(){
        return positions;
    }

    public Square getPositionSquareByIndex(int index){
        return positions.get(index);
    }

    public ArrayList<Square> getAllPositionSquaresWithPiece(){
        return positions.stream().filter(Square::hasPiece).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Square> getAllTeamPositionSquares(TeamEnum team){
        return positions.stream().filter(p-> p.hasPiece() && p.getPiece().getTeam() == team).collect(Collectors.toCollection(ArrayList::new));
    }

    public TeamEnum getTeamTurn() {
        return teamTurn;
    }

    public void changeTeamTurn() {
        this.teamTurn = teamTurn == TeamEnum.White ? TeamEnum.Black : TeamEnum.White;
        if(teamTurn == initialPosition.getInitialTeam()){
            this.actualTurn++;
        }
        updateObservers();
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void activeSelectedPiece(){
        setSelectedPiece(selectedPiece);
    }
    public void setSelectedPiece(@Null Piece selectedPiece) {
        if(gameOver) return;
        this.selectedPiece = selectedPiece;
        if(selectedPiece == null) return;
        Square selectedSquare= selectedPiece.getSquare();
        selectedSquare.setState(SquareStateTypeEnum.Selected);
        ArrayList<Integer> possiblePositionMovements = selectedPiece.getPossibleMovements();
        for(int possiblePositionIndex : possiblePositionMovements){
            Square possiblePositionSquare = positions.get(possiblePositionIndex);
            possiblePositionSquare.setState(SquareStateTypeEnum.Possible);
        }
    }

    public int move(Square selectedSquare){
        if(gameOver) return -1;
        promotionSelector = null;
        int res = contextRules.move(selectedPiece.getSquare(), selectedSquare);
        if(res >= 0){
            this.isInitialGame = false;
            if(res != 2){
                changeTeamTurn();
            }
            else{
                promotionSelector = new PromotionSelector(this, selectedSquare);
            }
            selectedPiece = null;
        }
        return res;
    }

    public int completePromotion(PieceTypeEnum pieceType){
        Square selectedSquare = promotionSelector.getSquarePieceToPromote();
        changeTeamTurn();
        return contextRules.promote(selectedSquare, pieceType);
    }

    public void clearPromotion(){
        promotionSelector = null;
    }



    public void setWinner(){
        if(contextRules.getClass() == AntiChessContextRules.class) {
            TeamEnum currentTeam = (getTeamTurn() == TeamEnum.White) ? TeamEnum.Black : TeamEnum.White;
            Gdx.app.debug(String.format("%s's win", currentTeam), "");
            teamWin = currentTeam;
            gameOver = true;
            return;
        }
        Gdx.app.debug(String.format("%s's win", teamTurn), "");
        teamWin = teamTurn;
        gameOver = true;
        updateObservers();
    }

    public void setWinner(TeamEnum team){
        Gdx.app.debug(String.format("%s's win", team), "");
        teamWin = team;
        gameOver = true;
        updateObservers();
    }

    public void setDraw(){
        Gdx.app.debug(String.format("Game end in draw", teamTurn), "");
        gameOver = true;
    }

    public TeamEnum getWinner(){
        return teamWin;
    }

    public PromotionSelector getPromotionSelector(){
        return promotionSelector;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public ContextRules getContextRules() {
        return contextRules;
    }
    
    public void addObserver(Observer newObserver){
        this.boardObserver.add(newObserver);
    }
    public void removeObserver(Observer removeObserver){
        this.boardObserver.remove(removeObserver);
    }

    private void updateObservers(){
        for(Observer observer : boardObserver){
            observer.update(this);
        }
    }

    public int getActualTurn(){
        return actualTurn;
    }

}
