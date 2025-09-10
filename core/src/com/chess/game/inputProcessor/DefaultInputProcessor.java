package com.chess.game.inputProcessor;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.chess.game.utils.Config;

public class DefaultInputProcessor implements InputProcessor{
    private int x;
    private int y;
    private boolean isClicking = false;
    @Override
    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {
        this.x = x;
        this.y = Config.HEIGHT - y;
        this.isClicking = button == Buttons.LEFT;
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        this.isClicking = false;
        return false;
    }

    @Override
    public boolean touchCancelled(int x, int y, int pointer, int button) {
        this.isClicking = false;
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (float amountX, float amountY) {
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isClicking() {
        return isClicking;
    }
}
