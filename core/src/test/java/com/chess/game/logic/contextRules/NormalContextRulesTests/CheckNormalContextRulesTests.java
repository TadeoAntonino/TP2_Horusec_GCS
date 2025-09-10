package com.chess.game.logic.contextRules.NormalContextRulesTests;

import com.chess.game.GameConfiguration;
import com.chess.game.initialPositions.CustomPosition;
import com.chess.game.initialPositions.InitialPositions;
import com.chess.game.logic.Board;
import com.chess.game.logic.Piece;
import com.chess.game.logic.contextRules.NormalContextRules;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.utils.PositionTranslate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CheckNormalContextRulesTests {

    @ParameterizedTest(name = "Validate check: {0}")
    @MethodSource("provideArgumentsOf_possibleCastlingMovementTests")
    void possibleCastlingMovementTests(String description, CustomPosition initialPositions, Piece selectedPiece, boolean isChecked, boolean isCheckMate) {
        // Arrange
        NormalContextRules normalContextRules = new NormalContextRules();
        GameConfiguration gameConfiguration = new GameConfiguration(initialPositions,normalContextRules);
        Board board = new Board(gameConfiguration);
        board.setSelectedPiece(selectedPiece);
        // Act
        ArrayList<Integer> actualPossibleMovementIndex = selectedPiece.getPossibleMovements();
        // Assert
        Assertions.assertEquals(!isCheckMate, !actualPossibleMovementIndex.isEmpty());
        Assertions.assertEquals(isChecked, selectedPiece.isChecked());
        //Assertions.assertEquals(isCheckMate, board.getWinner() != null);
    }
    private static Stream<Arguments> provideArgumentsOf_possibleCastlingMovementTests() {
        return Stream.of(
                getWhiteKingInCheckNotCheckmateByBlackRook(),
                getWhiteKingInCheckNotCheckmateByBlackQueen(),
                getWhiteKingInCheckNotCheckmateByBlackBishop(),
                getWhiteKingInCheckNotCheckmateByBlackKnight(),
                getWhiteKingInCheckNotCheckmateByBlackPawn(),
                getWhiteKingNotInCheckByBlackRook(),
                getWhiteKingNotInCheckByBlackQueen(),
                getWhiteKingNotInCheckByBlackBishop(),
                getWhiteKingNotInCheckByBlackKnight(),
                getWhiteKingNotInCheckByBlackPawn(),
                getBlackKingInCheckNotCheckmateByWhiteRook(),
                getBlackKingInCheckNotCheckmateByWhiteQueen(),
                getBlackKingInCheckNotCheckmateByWhiteBishop(),
                getBlackKingInCheckNotCheckmateByWhiteKnight(),
                getBlackKingInCheckNotCheckmateByWhitePawn(),
                getBlackKingNotInCheckByWhiteRook(),
                getBlackKingNotInCheckByWhiteQueen(),
                getBlackKingNotInCheckByWhiteBishop(),
                getBlackKingNotInCheckByWhiteKnight(),
                getBlackKingNotInCheckByWhitePawn(),
                getBlackKingNotInCheckByWhiteRookBlockedByPiece(),
                getBlackKingNotInCheckByWhiteBishopBlockedByPiece(),
                getBlackKingNotInCheckByWhiteQueenBlockedByPiece(),
                getWhiteKingNotInCheckByBlackRookBlockedByPiece(),
                getWhiteKingNotInCheckByBlackBishopBlockedByPiece(),
                getWhiteKingNotInCheckByBlackQueenBlockedByPiece(),
                getWhiteKingNotInCheckByBlackKnightBlockedByPiece()
        );
    }

   private static Arguments getWhiteKingInCheckNotCheckmateByBlackRook() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e5"), PieceTypeEnum.Rook, TeamEnum.Black, false);
        return Arguments.of("White king in check but not checkmate by black rook", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), true, false);
    }


    private static Arguments getWhiteKingInCheckNotCheckmateByBlackQueen() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e3"), PieceTypeEnum.Queen, TeamEnum.Black, false);
        return Arguments.of("White king in check but not checkmate by black queen",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), true, false);
    }
    private static Arguments getWhiteKingInCheckNotCheckmateByBlackBishop() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c3"), PieceTypeEnum.Bishop, TeamEnum.Black, false);
        return Arguments.of("White king in check but not checkmate by black bishop",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), true, false);
    }
    private static Arguments getWhiteKingInCheckNotCheckmateByBlackKnight() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("g2"), PieceTypeEnum.Knight, TeamEnum.Black, false);
        return Arguments.of("White king in check but not checkmate by black knight",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), true, false);
    }
    private static Arguments getWhiteKingInCheckNotCheckmateByBlackPawn() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d2"), PieceTypeEnum.Pawn, TeamEnum.Black, false);
        return Arguments.of("White king in check but not checkmate by black pawn",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), true, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackRook() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a4"), PieceTypeEnum.Rook, TeamEnum.Black, false);
        return Arguments.of("White king not in check by black rook", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackQueen() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d3"), PieceTypeEnum.Queen, TeamEnum.Black, false);
        return Arguments.of("White king not in check by black queen",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackBishop() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c4"), PieceTypeEnum.Bishop, TeamEnum.Black, false);
        return Arguments.of("White king not in check by black bishop",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackKnight() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("g3"), PieceTypeEnum.Knight, TeamEnum.Black, false);
        return Arguments.of("White king not in check by black knight",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackPawn() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d6"), PieceTypeEnum.Pawn, TeamEnum.Black, false);
        return Arguments.of("White king not in check by black pawn",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getBlackKingInCheckNotCheckmateByWhiteRook() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e4"), PieceTypeEnum.Rook, TeamEnum.White, false);
        return Arguments.of("Black king in check but not checkmate by white rook",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), true, false);
    }
    private static Arguments getBlackKingInCheckNotCheckmateByWhiteQueen() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e7"), PieceTypeEnum.Queen, TeamEnum.White, false);
        return Arguments.of("Black king in check but not checkmate by white queen",initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), true, false);
    }
    private static Arguments getBlackKingInCheckNotCheckmateByWhiteBishop() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c6"), PieceTypeEnum.Bishop, TeamEnum.White, false);
        return Arguments.of("Black king in check but not checkmate by white bishop", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), true, false);
    }
    private static Arguments getBlackKingInCheckNotCheckmateByWhiteKnight() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("f6"), PieceTypeEnum.Knight, TeamEnum.White, false);
        return Arguments.of("Black king in check but not checkmate by white knight", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), true, false);
    }
    private static Arguments getBlackKingInCheckNotCheckmateByWhitePawn() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d7"), PieceTypeEnum.Pawn, TeamEnum.White, false);
        return Arguments.of("Black king in check but not checkmate by white pawn", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), true, false);
    }

    private static Arguments getBlackKingNotInCheckByWhiteRook() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a4"), PieceTypeEnum.Rook, TeamEnum.White, false);
        return Arguments.of("Black king not in check by white rook", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }

    private static Arguments getBlackKingNotInCheckByWhiteQueen() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d3"), PieceTypeEnum.Queen, TeamEnum.White, false);
        return Arguments.of("Black king not in check by white queen", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }

    private static Arguments getBlackKingNotInCheckByWhiteBishop() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b4"), PieceTypeEnum.Bishop, TeamEnum.White, false);
        return Arguments.of("Black king not in check by white bishop", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }

    private static Arguments getBlackKingNotInCheckByWhiteKnight() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("g3"), PieceTypeEnum.Knight, TeamEnum.White, false);
        return Arguments.of("Black king not in check by white knight", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }

    private static Arguments getBlackKingNotInCheckByWhitePawn() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d6"), PieceTypeEnum.Pawn, TeamEnum.White, false);
        return Arguments.of("Black king not in check by white pawn", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }


    private static Arguments getBlackKingNotInCheckByWhiteRookBlockedByPiece() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e4"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e6"), PieceTypeEnum.Pawn, TeamEnum.Black, false);
        return Arguments.of("Black king not in check due to black pawn blocking white rook", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }

    private static Arguments getBlackKingNotInCheckByWhiteBishopBlockedByPiece() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c6"), PieceTypeEnum.Bishop, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d7"), PieceTypeEnum.Pawn, TeamEnum.Black, false);
        return Arguments.of("Black king not in check due to black pawn blocking white bishop", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }

    private static Arguments getBlackKingNotInCheckByWhiteQueenBlockedByPiece() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e5"), PieceTypeEnum.Queen, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e7"), PieceTypeEnum.Pawn, TeamEnum.Black, false);
        return Arguments.of("Black king not in check due to black pawn blocking white queen", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), false, false);
    }
    private static Arguments getWhiteKingNotInCheckByBlackRookBlockedByPiece() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e6"), PieceTypeEnum.Rook, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e4"), PieceTypeEnum.Pawn, TeamEnum.White, false);
        return Arguments.of("White king not in check due to white pawn blocking black rook", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackBishopBlockedByPiece() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c6"), PieceTypeEnum.Bishop, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d7"), PieceTypeEnum.Pawn, TeamEnum.White, false);
        return Arguments.of("White king not in check due to white pawn blocking black bishop", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackQueenBlockedByPiece() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e7"), PieceTypeEnum.Queen, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e5"), PieceTypeEnum.Pawn, TeamEnum.White, false);
        return Arguments.of("White king not in check due to white pawn blocking black queen", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }

    private static Arguments getWhiteKingNotInCheckByBlackKnightBlockedByPiece() {
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("f6"), PieceTypeEnum.Knight, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e7"), PieceTypeEnum.Bishop, TeamEnum.White, false);
        return Arguments.of("White king not in check due to white bishop blocking black knight", initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), false, false);
    }


}
