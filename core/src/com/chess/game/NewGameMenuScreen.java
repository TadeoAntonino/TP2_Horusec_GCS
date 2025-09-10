package com.chess.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chess.game.renderer.TextButtonRenderer;
import com.chess.game.utils.Render;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class NewGameMenuScreen extends BackgroundScreen implements Screen {
    private VerticalGroup menuItems;
    private Stage stage;
    private TextButtonRenderer startButton;
    private TextButtonRenderer changeModeButton;
    private TextButtonRenderer returnButton;
    private TextButtonRenderer exitButton;
    private ChessGame game;
    public NewGameMenuScreen(ChessGame game){
        stage = new Stage(new ScreenViewport());
        menuItems = new VerticalGroup();
        menuItems.setFillParent(true);
        menuItems.space(20);
        menuItems.center();
        stage.addActor(menuItems);

        this.game = game;
    }
    @Override
    public void show() {
        super.show();
        startButton =  new TextButtonRenderer("Iniciar\r\nPartida",game.getSkin(),"default");
//        startButton.setSize(120,40);
//        startButton.setPosition(700,300);
        menuItems.addActor(startButton);
        startButton.setAction(this::startGame);

        changeModeButton =  new TextButtonRenderer("Modo:\r\n" + game.ChessGameState.getGameConfigurationBuilder().GetSelectedModeGameName(), game.getSkin(),"default");
//        changeModeButton.setSize(120,40);
//        changeModeButton.setPosition(700,250);
        menuItems.addActor(changeModeButton);
        changeModeButton.setAction(this::changeModeScreen);

        returnButton =  new TextButtonRenderer("Volver",game.getSkin(),"default");
//        returnButton.setSize(120,40);
//        returnButton.setPosition(700,200);
        menuItems.addActor(returnButton);
        returnButton.setAction(this::returnScreen);

        exitButton =  new TextButtonRenderer("Salir",game.getSkin(),"default");
//        exitButton.setSize(120,40);
//        exitButton.setPosition(700,150);
        menuItems.addActor(exitButton);
        exitButton.setAction(game::exitGame);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.GRAY);
        stage.draw();

        if (game.inputProcessor.isClicking()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
            Gdx.app.debug("Coordenada X click: ", game.inputProcessor.getX() + "");
            Gdx.app.debug("Coordenada Y click: ", game.inputProcessor.getY() + "");
            if (startButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())) {
                startButton.runAction();
            }
            ;
            if (changeModeButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())) {
                changeModeButton.runAction();
            }
            ;
            if (returnButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())) {
                returnButton.runAction();
            }
            ;
            if (exitButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())) {
                exitButton.runAction();
            }
            ;

        }
        super.render(v);
        startButton.draw(Render.Batch, 1);
        changeModeButton.draw(Render.Batch, 1);
        returnButton.draw(Render.Batch, 1);
        exitButton.draw(Render.Batch, 1);
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

    }

    private void startGame(int test){
        this.game.ChessGameState.setActiveScreen(ScreenEnum.Board);
    }

    private void returnScreen(int test){
        this.game.ChessGameState.setActiveScreen(ScreenEnum.MainMenu);
    }

    private void changeModeScreen(int test){
        this.game.ChessGameState.setActiveScreen(ScreenEnum.ChangeModeMenu);
    }


}
