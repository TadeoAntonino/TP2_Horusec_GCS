package com.chess.game.renderer;

public class BoardRendererOptions {
    private boolean showPossibleMovements;

    public BoardRendererOptions(){
        showPossibleMovements = false;
    }

    public boolean isShowPossibleMovements() {
        return showPossibleMovements;
    }

    public void setShowPossibleMovements(boolean showPossibleMovements) {
        this.showPossibleMovements = showPossibleMovements;
    }
}
