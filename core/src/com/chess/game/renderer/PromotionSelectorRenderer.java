package com.chess.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chess.game.logic.PromotionSelector;
import com.chess.game.logic.Square;
import com.chess.game.logic.contextRules.AntiChessContextRules;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.resources.Images;
import com.chess.game.resources.Sounds;
import com.chess.game.utils.Config;
import com.chess.game.utils.LocalAssetManager;
import com.chess.game.utils.SoundPlayer;
import com.chess.game.logic.Board;
public class PromotionSelectorRenderer {
    private PromotionSelector promotionSelector;
    private float separation;
    private float initialBoardX;
    private float initialBoardY;
    private float finalXCord;
    private float finalYCord;

    public PromotionSelectorRenderer(PromotionSelector promotionSelector, float separation){

        this.promotionSelector = promotionSelector;
        this.separation = separation;
        Square squareToPromote = promotionSelector.getSquarePieceToPromote();


  this.initialBoardX = squareToPromote.getInitialXCord();
        this.initialBoardY = (promotionSelector.getTeam() == TeamEnum.White
                ? squareToPromote.getInitialYCord() - (3* (Config.SQUARE_LENGTH + separation))
                : squareToPromote.getInitialYCord());

        finalXCord = initialBoardX + Config.SQUARE_LENGTH;
        if(promotionSelector.getBoard().getContextRules().getClass()==AntiChessContextRules.class ) {
            finalYCord = initialBoardY + (5 * (Config.SQUARE_LENGTH + separation));
        }
        else{
            finalYCord = initialBoardY + (4 * (Config.SQUARE_LENGTH + separation));
        }

    }

    public void draw(SpriteBatch batch){
        Texture texture =  LocalAssetManager.AssetManager.get(Images.SquarePromoteSelector, Texture.class);
        Square squareToPromote = promotionSelector.getSquarePieceToPromote();
        if (promotionSelector.getBoard().getContextRules().getClass()== AntiChessContextRules.class) {
            for (int i = 0; i < 5; i++) {
                Square square = promotionSelector.getSquareToSelect(i);
                Sprite spriteSquare = new Sprite(texture);
                float initialXCord = squareToPromote.getInitialXCord();
                float initialYCord = squareToPromote.getInitialYCord() + ((promotionSelector.getTeam() == TeamEnum.White ? -1 : 1) * i * (Config.SQUARE_LENGTH + separation));
                float finalXCord = initialXCord + Config.SQUARE_LENGTH;
                float finalYCord = initialYCord + Config.SQUARE_LENGTH;
                square.setCord(initialXCord, initialYCord, finalXCord, finalYCord);
                spriteSquare.setPosition(initialXCord, initialYCord);
                spriteSquare.setSize(Config.SQUARE_LENGTH, Config.SQUARE_LENGTH + separation);
                spriteSquare.draw(batch);
                if (square.hasPiece()) {
                    Sprite pieceSprite = PieceRenderer.getPieceRenderer(square.getPiece()).getPieceSprite();
                    pieceSprite.setBounds(square.getInitialXCord(), square.getInitialYCord(), Config.SQUARE_LENGTH, Config.SQUARE_LENGTH);
                    pieceSprite.draw(batch);
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            Square square = promotionSelector.getSquareToSelect(i);
            Sprite spriteSquare = new Sprite(texture);
            float initialXCord = squareToPromote.getInitialXCord();
            float initialYCord = squareToPromote.getInitialYCord() + ((promotionSelector.getTeam() == TeamEnum.White ? -1 : 1) * i * (Config.SQUARE_LENGTH + separation));
            float finalXCord = initialXCord + Config.SQUARE_LENGTH;
            float finalYCord = initialYCord + Config.SQUARE_LENGTH;
            square.setCord(initialXCord, initialYCord, finalXCord, finalYCord);
            spriteSquare.setPosition(initialXCord, initialYCord);
            spriteSquare.setSize(Config.SQUARE_LENGTH, Config.SQUARE_LENGTH + separation);
            spriteSquare.draw(batch);
            if (square.hasPiece()) {
                Sprite pieceSprite = PieceRenderer.getPieceRenderer(square.getPiece()).getPieceSprite();
                pieceSprite.setBounds(square.getInitialXCord(), square.getInitialYCord(), Config.SQUARE_LENGTH, Config.SQUARE_LENGTH);
                pieceSprite.draw(batch);
            }
        }
    }

    private boolean isClickMe(int cordX, int cordY){
        return initialBoardX <= cordX
                && finalXCord >= cordX
                && initialBoardY <= cordY
                && finalYCord >= cordY;
    }
    public void click(int x, int y) {
        if (promotionSelector.getBoard().getContextRules().getClass() == AntiChessContextRules.class) {
            Square squareSelected = promotionSelector.getSquarePieceSelected(x, y);
            if (squareSelected == null) {
                SoundPlayer.play(Sounds.IncorrectMove);
                return;
            }
            if (isClickMe(x, y)) {
                Gdx.app.debug("PromotionSelector", "Is Click me");
                Board board = promotionSelector.getBoard();
                int res = board.completePromotion(squareSelected.getPiece().getPieceType());
                if (res < 0) {
                    SoundPlayer.play(Sounds.IncorrectMove);
                } else {
                    board.clearPromotion();
                    SoundPlayer.play(Sounds.Move);
                }
            } else if (promotionSelector.getSquarePieceSelected(x, y).getPiece().getTeam() == TeamEnum.White && promotionSelector.getSquarePieceSelected(x, y).getPiece().getPieceType() == PieceTypeEnum.King) {
                Gdx.app.debug("PromotionSelector", "Is Click me");
                Board board = promotionSelector.getBoard();
                int res = board.completePromotion(squareSelected.getPiece().getPieceType());
                if (res < 0) {
                    SoundPlayer.play(Sounds.IncorrectMove);
                } else {
                    board.clearPromotion();
                    SoundPlayer.play(Sounds.Move);
                }
            }
        } else {
            if (!isClickMe(x, y)) {
                return;
            }
            Gdx.app.debug("PromotionSelector", "Is Click me");
            Square squareSelected = promotionSelector.getSquarePieceSelected(x, y);
            if (squareSelected == null) {
                SoundPlayer.play(Sounds.IncorrectMove);
                return;
            }
            Board board = promotionSelector.getBoard();
            int res = board.completePromotion(squareSelected.getPiece().getPieceType());
            if (res < 0) {
                SoundPlayer.play(Sounds.IncorrectMove);
            } else {
                board.clearPromotion();
                SoundPlayer.play(Sounds.Move);
            }
        }
    }
}
