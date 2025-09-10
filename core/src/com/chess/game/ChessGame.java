package com.chess.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.chess.game.inputProcessor.DefaultInputProcessor;
import com.chess.game.utils.LocalAssetManager;
import com.chess.game.utils.Render;
import com.chess.game.resources.Images;
import com.chess.game.resources.Sounds;
import org.w3c.dom.Text;

public class ChessGame extends Game {

	Skin skin;
	public DefaultInputProcessor inputProcessor;
	public ChessGameState ChessGameState;
	private Sprite Logo;

	private Label PercentageLabel;

	private int loadedSleep = 30;
	@Override
	public void create () {

		float logoSizeX = 1417;
		float logoSizeY = 1650;
		float reduceIndex = 0.35f;
		float logoYPosition = 50f;
		float logoXPosition = 350f;
		skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
		skin.getFont("font-button").getData().setScale(1.0f);


		Texture logoTexture = new Texture(Images.Logo);
		Logo = new Sprite(logoTexture);
		Logo.setSize(logoSizeX * reduceIndex,  logoSizeY * reduceIndex);
		Logo.setPosition(logoXPosition,logoYPosition);

		PercentageLabel = new Label("Cargando... 0 %", skin);
		PercentageLabel.setPosition(logoXPosition + (logoSizeX * reduceIndex / 3), logoYPosition);
		PercentageLabel.setColor(Color.WHITE);
		PercentageLabel.setFontScale(1.75f);

		ChessGameState =  new ChessGameState(this);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Render.Batch = new SpriteBatch();
		LocalAssetManager.AssetManager = new AssetManager();
		inputProcessor = new DefaultInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		LocalAssetManager.AssetManager.load(Images.BBishop, Texture.class);
		LocalAssetManager.AssetManager.load(Images.BKing, Texture.class);
		LocalAssetManager.AssetManager.load(Images.BKnight, Texture.class);
		LocalAssetManager.AssetManager.load(Images.BPawn, Texture.class);
		LocalAssetManager.AssetManager.load(Images.BQueen, Texture.class);
		LocalAssetManager.AssetManager.load(Images.BRook, Texture.class);
		LocalAssetManager.AssetManager.load(Images.WBishop, Texture.class);
		LocalAssetManager.AssetManager.load(Images.WKing, Texture.class);
		LocalAssetManager.AssetManager.load(Images.WKnight, Texture.class);
		LocalAssetManager.AssetManager.load(Images.WPawn, Texture.class);
		LocalAssetManager.AssetManager.load(Images.WQueen, Texture.class);
		LocalAssetManager.AssetManager.load(Images.WRook, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownDark, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownLight, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareGrayDark, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareGrayLight, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquarePromoteSelector, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownDarkSelected, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownLightSelected, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownDarkMoved, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownLightMoved, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownDarkPossible, Texture.class);
		LocalAssetManager.AssetManager.load(Images.SquareBrownLightPossible, Texture.class);
		LocalAssetManager.AssetManager.load(Images.Background, Texture.class);
		LocalAssetManager.AssetManager.load(Images.Title, Texture.class);
		LocalAssetManager.AssetManager.load(Sounds.Move, Sound.class);
		LocalAssetManager.AssetManager.load(Sounds.Capture, Sound.class);
		LocalAssetManager.AssetManager.load(Sounds.IncorrectMove, Sound.class);
		LocalAssetManager.AssetManager.load(Sounds.AtomicCapture, Sound.class);
	}

	@Override
	public void render () {
		Render.Batch.begin();

		ScreenUtils.clear(Color.CLEAR);

		if(LocalAssetManager.AssetManager.update()){
			if(loadedSleep > 0){
				ShowLogoAndLoading();
				loadedSleep--;
			}
			else{
				if(this.screen == null || this.screen != ChessGameState.getActiveScreen()){
					Gdx.app.debug("Set Screen ", ChessGameState.getActiveScreen().toString());
					this.setScreen(ChessGameState.getActiveScreen());
				}
			}

		}
		else {
			ShowLogoAndLoading();
		}

		super.render();
		Render.Batch.end();
	}

	private void ShowLogoAndLoading() {
		Logo.draw(Render.Batch);
		float progress = LocalAssetManager.AssetManager.getProgress();
		PercentageLabel.setText("Cargando... " + Math.round(100 * progress) + " %" );
		PercentageLabel.draw(Render.Batch, 1);
		Gdx.app.debug("Loading ", 100 * progress +"%");
	}

	@Override
	public void dispose () {
		Render.Batch.dispose();
		LocalAssetManager.AssetManager.dispose();
	}

	public Skin getSkin(){
		return skin;
	}

	public void exitGame(int test){
		Gdx.app.exit();
	}
}
