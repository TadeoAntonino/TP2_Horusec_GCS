package com.chess.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chess.game.resources.Images;
import com.chess.game.utils.LocalAssetManager;
import com.chess.game.utils.Render;

public class BackgroundScreen implements Screen {
    private Sprite Background;
    private Sprite Title;

    protected Boolean ShowTitle = true;

    @Override
    public void show() {
        float lalal = 0.33f;
        float backgroundWidth = 3885f;
        float backgroundHeight = 2322f;

        Background = new Sprite(LocalAssetManager.AssetManager.get(Images.Background, Texture.class));
        Background.setSize(backgroundWidth *lalal,  backgroundHeight*lalal);
        Background.setPosition(0,0);

        Title = new Sprite(LocalAssetManager.AssetManager.get(Images.Title, Texture.class));
        Title.setSize(813 * 1.1f,  74 * 1.1f);
        Title.setPosition(200,450);
    }

    @Override
    public void render(float v) {
        Background.draw(Render.Batch, .45f);
        if(ShowTitle){
            Title.draw(Render.Batch, 1);
        }
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


}
