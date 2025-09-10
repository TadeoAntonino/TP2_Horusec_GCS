package com.chess.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chess.game.initialPositions.InitialPositionType;
import com.chess.game.logic.contextRules.ContextRuleType;
import com.chess.game.renderer.TextButtonRenderer;
import com.chess.game.utils.Render;

public class ChangeModeMenuScreen extends BackgroundScreen implements Screen {
    private Table menuItems;
    private Stage stage;

    private TextButtonRenderer normalModeButton;
    private TextButtonRenderer upDownModeButton;
    private TextButtonRenderer fischerModeButton;
    private TextButtonRenderer transcendentalModeButton;
    private TextButtonRenderer atomicModeButton;
    private TextButtonRenderer antiChessModeButton;
    private TextButtonRenderer returnButton;
    private TextButtonRenderer exitButton;
    private ChessGame game;
    public ChangeModeMenuScreen(ChessGame game){
        stage = new Stage(new ScreenViewport());
        menuItems = new Table();
//        menuItems.setDebug(true);
        menuItems.setFillParent(true);
        stage.addActor(menuItems);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        normalModeButton =  new TextButtonRenderer("Normal",game.getSkin(),"default");
//        normalModeButton.setSize(130,40);
//        normalModeButton.setPosition(630,350);
        menuItems.add(normalModeButton).fillX().pad(10);
        normalModeButton.setAction(this::setNormalMode);

        upDownModeButton =  new TextButtonRenderer("Arriba-Abajo",game.getSkin(),"default");
//        upDownModeButton.setSize(130,40);
//        upDownModeButton.setPosition(770,350);
        menuItems.add(upDownModeButton).fillX().pad(10);
        upDownModeButton.setAction(this::setUpDownMode);

        menuItems.row();

        fischerModeButton =  new TextButtonRenderer("Aleatorio de Fischer",game.getSkin(),"default");
//        fischerModeButton.setSize(130,40);
//        fischerModeButton.setPosition(630,300);
        menuItems.add(fischerModeButton).fillX().pad(10);
        fischerModeButton.setAction(this::fischerMode);

        transcendentalModeButton =  new TextButtonRenderer("Trascendental",game.getSkin(),"default");
//        transcendentalModeButton.setSize(130,40);
//        transcendentalModeButton.setPosition(770,300);
        menuItems.add(transcendentalModeButton).fillX().pad(10);
        transcendentalModeButton.setAction(this::transcendentalMode);

        menuItems.row();

        atomicModeButton =  new TextButtonRenderer("Atomico",game.getSkin(),"default");
//        atomicModeButton.setSize(130,40);
//        atomicModeButton.setPosition(630,250);
        menuItems.add(atomicModeButton).fillX().pad(10);
        atomicModeButton.setAction(this::atomicMode);

        antiChessModeButton =  new TextButtonRenderer("Anti-ajedrez",game.getSkin(),"default");
//        antiChessModeButton.setSize(130,40);
//        antiChessModeButton.setPosition(770,250);
        menuItems.add(antiChessModeButton).fillX().pad(10);
        antiChessModeButton.setAction(this::antiChessMode);

        menuItems.row();

        returnButton =  new TextButtonRenderer("Volver",game.getSkin(),"default");
//        returnButton.setSize(130,40);
//        returnButton.setPosition(700,200);
        menuItems.add(returnButton).fillX().pad(10);
        returnButton.setAction(this::returnScreen);

        exitButton =  new TextButtonRenderer("Salir",game.getSkin(),"default");
//        exitButton.setSize(130,40);
//        exitButton.setPosition(700,150);
        menuItems.add(exitButton).fillX().pad(10);
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
            if(normalModeButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                normalModeButton.runAction();
            };
            if(upDownModeButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                upDownModeButton.runAction();
            };
            if(fischerModeButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                fischerModeButton.runAction();
            };
            if(transcendentalModeButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                transcendentalModeButton.runAction();
            };
            if(atomicModeButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                atomicModeButton.runAction();
            };
            if(antiChessModeButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                antiChessModeButton.runAction();
            };
            if(returnButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                returnButton.runAction();
            };
            if(exitButton.isClickMe(game.inputProcessor.getX(), game.inputProcessor.getY())){
                exitButton.runAction();
            };

        }
        super.render(v);
        normalModeButton.draw(Render.Batch, 1);
        upDownModeButton.draw(Render.Batch, 1);
        fischerModeButton.draw(Render.Batch, 1);
        transcendentalModeButton.draw(Render.Batch, 1);
        atomicModeButton.draw(Render.Batch, 1);
        antiChessModeButton.draw(Render.Batch, 1);
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

    private void setNormalMode(int test){
        this.game.ChessGameState.getGameConfigurationBuilder()
                .SetContextRules(ContextRuleType.Normal)
                .SetInitialPosition(InitialPositionType.Normal);
        returnScreen(0);
    }

    private void setUpDownMode(int test){
        this.game.ChessGameState.getGameConfigurationBuilder()
                .SetContextRules(ContextRuleType.Normal)
                .SetInitialPosition(InitialPositionType.UpDown);
        returnScreen(0);
    }

    private void fischerMode(int test){
        this.game.ChessGameState.getGameConfigurationBuilder()
                .SetContextRules(ContextRuleType.Random)
                .SetInitialPosition(InitialPositionType.Fischer);
        returnScreen(0);
    }

    private void transcendentalMode(int test){
        this.game.ChessGameState.getGameConfigurationBuilder()
                .SetContextRules(ContextRuleType.Random)
                .SetInitialPosition(InitialPositionType.Transcendental);
        returnScreen(0);
    }

    private void atomicMode(int test){
        this.game.ChessGameState.getGameConfigurationBuilder()
                .SetContextRules(ContextRuleType.Atomic)
                .SetInitialPosition(InitialPositionType.Normal);
        returnScreen(0);
    }

    private void antiChessMode(int test){
        this.game.ChessGameState.getGameConfigurationBuilder()
                .SetContextRules(ContextRuleType.AntiChess)
                .SetInitialPosition(InitialPositionType.Normal);
        returnScreen(0);
    }

    private void returnScreen(int test){
        this.game.ChessGameState.setActiveScreen(ScreenEnum.NewGameMenu);
    }
}
