package com.chess.game.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.resources.Images;
import com.chess.game.utils.Config;
import com.chess.game.utils.LocalAssetManager;

import java.util.ArrayList;
import java.util.Optional;

public class PieceRenderer {
    private final Piece piece;
    private Sprite sprite;

    private static ArrayList<PieceRenderer> pieceRederers =  new ArrayList<>();

    public PieceRenderer(Piece piece){
        this.piece = piece;
    }

    public Sprite getPieceSprite(){
        sprite = new Sprite(LocalAssetManager.AssetManager.get(this.getFileName(), Texture.class));
        sprite.setSize(Config.SQUARE_LENGTH ,Config.SQUARE_LENGTH );
        return sprite;
    }

    public PieceTypeEnum getPieceType(){
        return piece.getPieceType();
    }

    public TeamEnum getPieceTeam(){
        return piece.getTeam();
    }

    private String getFileName(){
        switch (piece.getTeam()){
            case White:{
                switch (piece.getPieceType()){
                    case King: { return Images.WKing; }
                    case Queen: { return Images.WQueen; }
                    case Knight: { return Images.WKnight; }
                    case Bishop: { return Images.WBishop; }
                    case Rook: { return Images.WRook; }
                    case Pawn: { return Images.WPawn; }
                }
            }
            case Black:{
                switch (piece.getPieceType()){
                    case King: { return Images.BKing; }
                    case Queen: { return Images.BQueen; }
                    case Knight: { return Images.BKnight; }
                    case Bishop: { return Images.BBishop; }
                    case Rook: { return Images.BRook; }
                    case Pawn: { return Images.BPawn; }
                }
            }
        }
        return null;
    }

    public static PieceRenderer getPieceRenderer(Piece piece){
        Optional<PieceRenderer> optionalPieceRenderer = pieceRederers.stream().filter(p-> p.getPieceTeam() == piece.getTeam() && p.getPieceType() == piece.getPieceType()).findFirst();
        if(optionalPieceRenderer.isPresent()){
            return optionalPieceRenderer.get();
        }
        else{
            PieceRenderer pieceRenderer = new PieceRenderer(piece);
            pieceRederers.add(pieceRenderer);
            return pieceRenderer;
        }
    }
}
