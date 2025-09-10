package com.chess.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.chess.game.logic.Board;
import com.chess.game.logic.Piece;
import com.chess.game.logic.PromotionSelector;
import com.chess.game.logic.Square;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.SquareStateTypeEnum;
import com.chess.game.resources.Images;
import com.chess.game.resources.Sounds;
import com.chess.game.utils.Config;
import com.chess.game.utils.LocalAssetManager;
import com.chess.game.utils.SoundPlayer;

import java.util.ArrayList;
import java.util.Optional;

public class BoardRenderer {
    private final Board board;
    private ArrayList<PieceRenderer> pieceRenderers;
    private float initialBoardX = 0;
    private float initialBoardY = 0;
    private float separation = 2;
    private Sprite baseBoardSprite;
    private ArrayList<Texture> squareTextures;
    private ArrayList<Sprite> squareSprites;
    private ArrayList<Sprite> piecesSprites;
    private BoardRendererOptions boardRendererOptions;
    private PromotionSelectorRenderer promotionSelectorRenderer;

    public BoardRenderer(float initialBoardX, float initialBoardY, Board board){
        this.initialBoardX = initialBoardX;
        this.initialBoardY = initialBoardY;
        this.board = board;
        this.boardRendererOptions =  new BoardRendererOptions();
        boardRendererOptions.setShowPossibleMovements(true);
        pieceRenderers = new ArrayList<>();
        squareSprites = new ArrayList<>();
        piecesSprites = new ArrayList<>();
        squareTextures =  new ArrayList<>();
        baseBoardSprite = new Sprite(LocalAssetManager.AssetManager.get(Images.SquareGrayDark, Texture.class));
        baseBoardSprite.setSize(((Config.SQUARE_LENGTH + separation)*8)+8,((Config.SQUARE_LENGTH + separation)*8)+8);
        baseBoardSprite.setPosition(initialBoardX -5,initialBoardY-5);

        ArrayList<Square> positions = board.getAllPositionSquares();
        for(int i = 0; i < positions.size(); i++){
            Sprite spriteSquare;
            Square square = positions.get(i);

            spriteSquare =  new Sprite(getSquareTexture(i));

            float initialXCord = initialBoardX + ((i%8)*(Config.SQUARE_LENGTH + separation));
            float initialYCord = initialBoardY+((i/8)*(Config.SQUARE_LENGTH + separation));
            square.setCord(initialXCord,initialYCord,initialXCord + Config.SQUARE_LENGTH,initialYCord + Config.SQUARE_LENGTH);
            spriteSquare.setPosition(square.getInitialXCord(), square.getInitialYCord());
            spriteSquare.setSize(Config.SQUARE_LENGTH,Config.SQUARE_LENGTH);
            squareSprites.add(spriteSquare);
        }

    }

    public void draw(SpriteBatch batch){
        baseBoardSprite.draw(batch);
        ArrayList<Square> positions = board.getAllPositionSquares();
        for (Square position : positions) {
            Sprite squareSprite = squareSprites.get(position.getPosition());
            squareSprite.setTexture(getSquareTexture(position.getPosition(), position.getState()));
            squareSprite.draw(batch);
            if(position.hasPiece()){
                Sprite pieceSprite = PieceRenderer.getPieceRenderer(position.getPiece()).getPieceSprite();
                pieceSprite.setBounds(position.getInitialXCord(), position.getInitialYCord(), Config.SQUARE_LENGTH,Config.SQUARE_LENGTH);
                pieceSprite.draw(batch);
            }
        }

        PromotionSelector promotionSelector = board.getPromotionSelector();
        if(promotionSelector == null) {
            promotionSelectorRenderer = null;
            return;
        }
        promotionSelectorRenderer =  new PromotionSelectorRenderer(promotionSelector, separation);
        promotionSelectorRenderer.draw(batch);
    }

    private boolean isClickMe(int cordX, int cordY){
        return initialBoardX <= cordX
                && initialBoardX + (8*(Config.SQUARE_LENGTH + separation)) >= cordX
                && initialBoardY <= cordY
                && initialBoardY + (8*(Config.SQUARE_LENGTH + separation)) >= cordY;
    }
    public void click(int cordX, int cordY){
        if(board.isGameOver()) return;
        if(!isClickMe(cordX, cordY)) return;
        PromotionSelector promotionSelector = board.getPromotionSelector();
        if(promotionSelector != null){
            promotionSelectorRenderer.click(cordX, cordY);
        }
        else {
            regularClicking(cordX, cordY);
        }
    }
    private void regularClicking(int cordX, int cordY){
        ArrayList<Square> positions = board.getAllPositionSquares();
        for (Square position : positions) {
            position.setState(SquareStateTypeEnum.Normal);
        }

        Optional<Square> optionalClickedSquare = positions.stream().filter(square -> square.getInitialXCord() <= cordX
                && square.getFinalXCord() >= cordX
                && square.getInitialYCord() <= cordY
                && square.getFinalYCord() >= cordY).findFirst();

        if(optionalClickedSquare.isPresent()){
            Gdx.app.debug("Square found",  "");
            Square clickedSquare = optionalClickedSquare.get();
            if (board.getSelectedPiece() == null) {
                if(clickedSquare.hasPiece())
                {
                    if(clickedSquare.getPiece().getTeam() == board.getTeamTurn())
                    {
                        board.setSelectedPiece(clickedSquare.getPiece());
                    }
                    else{
                        Gdx.app.error("Piece selection error",  "Is player's " + board.getTeamTurn() + " turn");
                        Sound incorrectMoveSound = LocalAssetManager.AssetManager.get(Sounds.IncorrectMove, Sound.class);
                        incorrectMoveSound.play();
                    }
                }
            }
            else{
                if(!clickedSquare.hasPiece() || clickedSquare.getPiece().getTeam() != board.getTeamTurn()) {
                    tryMove(clickedSquare);
                }
                else{
                    if(clickedSquare.hasPiece()){
                        if (board.getSelectedPiece().getPieceType() == PieceTypeEnum.King
                            && clickedSquare.getPiece().getPieceType() == PieceTypeEnum.Rook
                            && clickedSquare.getPiece().getTeam() == board.getSelectedPiece().getTeam()) {
                            //Permitir el movimiento del rey a un casillero ocupado por una torre del mismo color, para enroque con posiciones iniciales aleatorias
                            //Si el movimiento no es posible, seleccionar la torre
                            int moveResult = tryMove(clickedSquare);
                            if (moveResult < 0) {
                                board.getSelectedPiece().getSquare().setState(SquareStateTypeEnum.Normal);
                                board.setSelectedPiece(clickedSquare.getPiece());
                            }
                        }
                        else {
                            board.setSelectedPiece(clickedSquare.getPiece());
                        }
                    }
                    else{
                        SoundPlayer.play(Sounds.IncorrectMove);
                        board.setSelectedPiece(null);
                    }

                }
            }

        }
        else{
            Gdx.app.error("Square not found",  "");
            board.activeSelectedPiece();
        }
    }

    private int tryMove(Square clickedSquare){
        int moveResult = board.move(clickedSquare);
        if (moveResult < 0) {
            SoundPlayer.play(Sounds.IncorrectMove);
            board.activeSelectedPiece();
        }
        if (moveResult == 0) {
            SoundPlayer.play(Sounds.Move);
        }
        if (moveResult > 0) {
            SoundPlayer.play(Sounds.Capture);
        }
        return moveResult;
    }

    private Texture getSquareTexture(int position){
        return getSquareTexture(position, SquareStateTypeEnum.Normal);
    }
    private Texture getSquareTexture(int position, SquareStateTypeEnum state){
        boolean isWhiteSquare = ((position / 8) % 2 == 0) == (position % 2 != 0);
        String fileName;
        switch (state){
            case Selected: { fileName =  isWhiteSquare ?  Images.SquareBrownLightSelected : Images.SquareBrownDarkSelected; break;}
            case Moved: { fileName =  isWhiteSquare ?  Images.SquareBrownLightMoved : Images.SquareBrownDarkMoved; break;}
            case Possible: {
                fileName = !boardRendererOptions.isShowPossibleMovements()
                        ? (isWhiteSquare ? Images.SquareBrownLight : Images.SquareBrownDark)
                        : (isWhiteSquare ? Images.SquareBrownLightPossible : Images.SquareBrownDarkPossible);
                break;
            }
            default: { fileName =  isWhiteSquare ?  Images.SquareBrownLight : Images.SquareBrownDark; break;}
        }
        return LocalAssetManager.AssetManager.get(fileName, Texture.class);
    }


}
