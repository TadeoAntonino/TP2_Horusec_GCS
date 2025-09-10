package com.chess.game;

import com.badlogic.gdx.Screen;

public class ChessGameState {

    private ScreenEnum ActiveScreenType;
    private Screen ActiveScreen;
    private ChessGame Game;
    private GameConfigurationBuilder GameConfigurationBuilder;
    public ChessGameState(ChessGame game){
        Game = game;
        GameConfigurationBuilder = new GameConfigurationBuilder();
        setActiveScreen(ScreenEnum.MainMenu);
    }

    public Screen getActiveScreen() {
        return ActiveScreen;
    }

    public void setActiveScreen(ScreenEnum activeScreen) {
        ActiveScreenType = activeScreen;
        switch (ActiveScreenType){
            case MainMenu: {
                ActiveScreen =  new MainMenuScreen(Game);
                break;
            }
            case Board:{
                ActiveScreen =  new BoardScreen(Game);
                break;
            }
            case NewGameMenu:{
                ActiveScreen =  new NewGameMenuScreen(Game);
                break;
            }
            case ChangeModeMenu:{
                ActiveScreen =  new ChangeModeMenuScreen(Game);
                break;
            }
        }
    }

    public com.chess.game.GameConfigurationBuilder getGameConfigurationBuilder() {
        return GameConfigurationBuilder;
    }
}
