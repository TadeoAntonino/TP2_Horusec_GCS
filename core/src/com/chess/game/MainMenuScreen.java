package com.chess.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chess.game.renderer.TextButtonRenderer;
import com.chess.game.resources.Images;
import com.chess.game.utils.Config;
import com.chess.game.utils.LocalAssetManager;
import com.chess.game.utils.Render;

public class MainMenuScreen extends BackgroundScreen implements Screen {
    private VerticalGroup menuItems;
    private Stage stage;
    private TextButtonRenderer newGameButton;
    private TextButtonRenderer exitButton;
    private ChessGame game;

    public MainMenuScreen(ChessGame game){
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
        newGameButton =  new TextButtonRenderer("Nuevo juego",game.getSkin(),"default");
//        newGameButton.setSize(120,40);
//        newGameButton.setPosition(700,250);
        menuItems.addActor(newGameButton);
        newGameButton.setAction(this::newGame);

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
            if(newGameButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                newGameButton.runAction();
            };
            if(exitButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                exitButton.runAction();
            };

        }
        super.render(v);
        newGameButton.draw(Render.Batch, 1);
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
        newGameButton = null;
        exitButton = null;
    }

    @Override
    public void dispose() {
        newGameButton = null;
        exitButton = null;
    }

    private void newGame(int test){
        this.game.ChessGameState.setActiveScreen(ScreenEnum.NewGameMenu);
    }
}
