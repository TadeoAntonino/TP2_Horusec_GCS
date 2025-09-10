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

class CastlingNormalContextRulesTest {

    @ParameterizedTest(name = "Validate possible castling moves")
    @MethodSource("provideArgumentsOf_possibleCastlingMovementTests")
    void possibleCastlingMovementTests(InitialPositions initialPositions, Piece selectedPiece, Integer expectedPossibleMovementIndex, boolean assertionCondition ) {
        //Arrange
        NormalContextRules normalContextRules = new NormalContextRules();
        GameConfiguration gameConfiguration = new GameConfiguration(initialPositions,normalContextRules);
        Board board = new Board(gameConfiguration);
        board.setSelectedPiece(selectedPiece);
        //Act
        ArrayList<Integer> actualPossibleMovementIndex = selectedPiece.getPossibleMovements();
        //Assert
        Assertions.assertEquals(assertionCondition, actualPossibleMovementIndex.contains(expectedPossibleMovementIndex));
    }
    private static Stream<Arguments> provideArgumentsOf_possibleCastlingMovementTests() {
        return Stream.of(
                getWhiteRightCastlingInitialPositionArguments(),
                getWhiteLeftCastlingInitialPositionArguments(),
                getNotWhiteRightCastlingByKingMovedInitialPositionArguments(),
                getNotWhiteLeftCastlingByKingMovedInitialPositionArguments(),
                getNotWhiteRightCastlingByRookMovedInitialPositionArguments(),
                getNotWhiteLeftCastlingByRookMovedInitialPositionArguments(),
                getNotWhiteRightCastlingByKingCheckedByFrontQueenInitialPositionArguments(),
                getNotWhiteLeftCastlingByKingCheckedByFrontQueenInitialPositionArguments(),
                getNotWhiteRightCastlingByKingCheckedBySideQueenInitialPositionArguments(),
                getNotWhiteLeftCastlingByKingCheckedBySideQueenInitialPositionArguments(),
                getNotWhiteRightCastlingByKingCheckedByRightDiagonalQueenInitialPositionArguments(),
                getNotWhiteRightCastlingByKingCheckedByLeftDiagonalQueenInitialPositionArguments(),
                getNotWhiteLeftCastlingByKingCheckedByRightDiagonalQueenInitialPositionArguments(),
                getNotWhiteLeftCastlingByKingCheckedByLeftDiagonalQueenInitialPositionArguments(),
                getNotWhiteRightCastlingByAttackedPathInitialPositionArguments(),
                getNotWhiteRightCastlingByAttackedPath2InitialPositionArguments(),
                getNotWhiteLeftCastlingByAttackedPathInitialPositionArguments(),
                getNotWhiteLeftCastlingByAttackedPath2InitialPositionArguments(),
                getWhiteLeftCastlingWithAttackedPath3InitialPositionArguments(),
                getNotWhiteRightCastlingBy_f1_BlockedPathInitialPositionArguments(),
                getNotWhiteRightCastlingBy_g1_BlockedPathInitialPositionArguments(),
                getNotWhiteLeftCastlingBy_d1_BlockedPathInitialPositionArguments(),
                getNotWhiteLeftCastlingBy_c1_BlockedPathInitialPositionArguments(),
                getNotWhiteLeftCastlingBy_b1_BlockedPathInitialPositionArguments(),
                getNotWhiteRightCastlingByAttackedPathWithPawnInitialPositionArguments(),
                getNotWhiteRightCastlingByAttackedPath2WithPawnInitialPositionArguments(),
                getNotWhiteLeftCastlingByAttackedPathWithPawnInitialPositionArguments(),
                getNotWhiteLeftCastlingByAttackedPath2WithPawnInitialPositionArguments(),
                getWhiteLeftCastlingByAttackedPath3WithPawnInitialPositionArguments(),
                getBlackRightCastlingInitialPositionArguments(),
                getBlackLeftCastlingInitialPositionArguments()

        );
    }
    private static Arguments getWhiteRightCastlingInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), true);
    }
    private static Arguments getWhiteLeftCastlingInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), true);
    }
    private static Arguments getNotWhiteRightCastlingByKingMovedInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByKingMovedInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByRookMovedInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByRookMovedInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByKingCheckedByFrontQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e7"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByKingCheckedByFrontQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e7"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByKingCheckedBySideQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByKingCheckedBySideQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByKingCheckedByRightDiagonalQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a5"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByKingCheckedByRightDiagonalQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a5"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByKingCheckedByLeftDiagonalQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h4"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByKingCheckedByLeftDiagonalQueenInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h4"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByAttackedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("f8"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByAttackedPath2InitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("g8"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByAttackedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d8"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByAttackedPath2InitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c8"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getWhiteLeftCastlingWithAttackedPath3InitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b8"), PieceTypeEnum.Queen, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), true);
    }
    private static Arguments getNotWhiteRightCastlingBy_f1_BlockedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("f1"), PieceTypeEnum.Queen, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteRightCastlingBy_g1_BlockedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("g1"), PieceTypeEnum.Queen, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingBy_d1_BlockedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d1"), PieceTypeEnum.Queen, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingBy_c1_BlockedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.Queen, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingBy_b1_BlockedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.Queen, TeamEnum.White, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByAttackedPathWithPawnInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("g2"), PieceTypeEnum.Pawn, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteRightCastlingByAttackedPath2WithPawnInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h2"), PieceTypeEnum.Pawn, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("g1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByAttackedPathWithPawnInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c2"), PieceTypeEnum.Pawn, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getNotWhiteLeftCastlingByAttackedPath2WithPawnInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b2"), PieceTypeEnum.Pawn, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }
    private static Arguments getWhiteLeftCastlingByAttackedPath3WithPawnInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a2"), PieceTypeEnum.Pawn, TeamEnum.Black, true);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), true);
    }
    private static Arguments getBlackRightCastlingInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h8"), PieceTypeEnum.Rook, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), PositionTranslate.getIndexByAlgebraicPosition("g8"), true);
    }
    private static Arguments getBlackLeftCastlingInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.Black);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a8"), PieceTypeEnum.Rook, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.White, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("e8")), PositionTranslate.getIndexByAlgebraicPosition("c8"), true);
    }
}
