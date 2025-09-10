package com.chess.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.chess.game.initialPositions.*;
import com.chess.game.logic.Board;
import com.chess.game.logic.Square;
import com.chess.game.logic.contextRules.AntiChessContextRules;
import com.chess.game.logic.contextRules.AtomicContextRules;
import com.chess.game.logic.contextRules.ContextRules;
import com.chess.game.logic.contextRules.NormalContextRules;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.renderer.BoardRenderer;
import com.chess.game.renderer.TextButtonRenderer;
import com.chess.game.utils.*;

import java.util.ArrayList;

public class BoardScreen extends BackgroundScreen implements Screen, Observer {
    private ChessGame game;
    private Board board;
    private ShapeRenderer shapeRenderer;
//    private Sprite baseBoardSprite;
//    private ArrayList<Sprite> squareSprites;
//    private ArrayList<Sprite> piecesSprites;
//    private Piece selectedPiece;
    float initialBoardX = 60f;
    float initialBoardY = 60f;
    BoardRenderer boardRenderer;
    private TextButtonRenderer resetButton;
    private TextButtonRenderer returnButton;
    private TextButtonRenderer turnInfo;

    public BoardScreen(final ChessGame game){
        Gdx.app.debug("Constructor", "");
        this.game = game;
//        squareSprites =  new ArrayList<>();
//        piecesSprites =  new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        super.show();
        ShowTitle = false;
        board = new Board(game.ChessGameState.getGameConfigurationBuilder().Build());
        board.addObserver(this);
        boardRenderer = new BoardRenderer(initialBoardX, initialBoardY, board);

        turnInfo = new TextButtonRenderer("Turno 1\nTurno de las\nblancas", game.getSkin(), "default");

        turnInfo.setSize(120, 120);
        turnInfo.setPosition(700, 400);

        resetButton = new TextButtonRenderer("Reiniciar\r\nPartida",game.getSkin(),"default");
        resetButton.setSize(120,40);
        resetButton.setPosition(700,250);
        resetButton.setAction(this::resetAction);

        returnButton =  new TextButtonRenderer("Abandonar\r\nPartida", game.getSkin(),"default");
        returnButton.setSize(120,40);
        returnButton.setPosition(700,200);
        returnButton.setAction(this::returnScreen);
        returnButton.setColor(Color.RED);

        Gdx.app.debug("Show", "");
    }

    @Override
    public void update(Board board){
        String newText = "Turno " + (board.getActualTurn()+1) + "\n";
        if (board.getWinner() != null) {
            if (board.getWinner() == TeamEnum.Black) {
                newText += "Han ganado las\nnegras";
            }
            else{
                newText += "Han ganado las\nblancas";
            }
        }
        else {
            if (board.getTeamTurn() == TeamEnum.Black) {
                newText += "Turno de las\nnegras";
            } else {
                newText += "Turno de las\nblancas";
            }
        }
        turnInfo.setText(newText);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.GRAY);
        if (game.inputProcessor.isClicking()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
            Gdx.app.debug("Coordenada X click: ", game.inputProcessor.getX() + "");
            Gdx.app.debug("Coordenada Y click: ", game.inputProcessor.getY() + "");
            boardRenderer.click(game.inputProcessor.getX(), game.inputProcessor.getY());
            if(resetButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                resetButton.runAction();
            };
            if(returnButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                returnButton.runAction();
            };
        }
        super.render(v);
        boardRenderer.draw(Render.Batch);
        resetButton.draw(Render.Batch, 1);
        returnButton.draw(Render.Batch, 1);
        turnInfo.draw(Render.Batch, 1);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void resetAction(int t) {
        Gdx.app.debug("Me Reinicio!!", "");
        board = new Board(game.ChessGameState.getGameConfigurationBuilder().Build());
        board.addObserver(this);
        boardRenderer = new BoardRenderer(initialBoardX, initialBoardY, board);
    }
    private void returnScreen(int t){
        this.game.ChessGameState.setActiveScreen(ScreenEnum.MainMenu);
    }
}
