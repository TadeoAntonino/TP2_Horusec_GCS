package com.chess.game.renderer;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.function.Consumer;

public class TextButtonRenderer extends TextButton {

    private Consumer<Integer> action;
    public TextButtonRenderer(String text, Skin skin) {
        super(text, skin);
    }

    public TextButtonRenderer(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public TextButtonRenderer(String text, TextButtonStyle style) {
        super(text, style);
    }



    public boolean isClickMe(int cordX, int cordY){
        return getX() <= cordX
                && getX() + getWidth() >= cordX
                && getY()  <= cordY
                && getY() + getHeight() >= cordY;
    }

    public void setAction(Consumer<Integer> action) {
        this.action = action;
    }

    public void runAction(){
        this.action.accept(0);
    }
}
