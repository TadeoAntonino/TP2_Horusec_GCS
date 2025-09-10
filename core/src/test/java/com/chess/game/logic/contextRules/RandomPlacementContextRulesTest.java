package com.chess.game.logic.contextRules;

import com.chess.game.GameConfiguration;
import com.chess.game.GameConfigurationBuilder;
import com.chess.game.initialPositions.CustomPosition;
import com.chess.game.initialPositions.FischerPosition;
import com.chess.game.initialPositions.InitialPositionType;
import com.chess.game.initialPositions.InitialPositions;
import com.chess.game.logic.Board;
import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.utils.PositionTranslate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RandomPlacementContextRulesTest {

    @ParameterizedTest(name = "Validate possible castling moves, expected {2} {3}")
    @MethodSource("provideArgumentsOf_possibleCastlingMovementTests")
    void possibleCastlingMovementTests(InitialPositions initialPositions, Piece selectedPiece, Integer expectedPossibleMovementIndex, boolean assertionCondition) {
        //Arrange
        RandomPlacementContextRules randomContextRules = new RandomPlacementContextRules();
        GameConfiguration gameConfiguration = new GameConfiguration(initialPositions, randomContextRules);
        Board board = new Board(gameConfiguration);
        board.setSelectedPiece(selectedPiece);
        //Act
        ArrayList<Integer> actualPossibleMovementIndex = selectedPiece.getPossibleMovements();
        //Assert
        Assertions.assertEquals(assertionCondition, actualPossibleMovementIndex.contains(expectedPossibleMovementIndex));
    }


    private static Stream<Arguments> provideArgumentsOf_possibleCastlingMovementTests() {
        return Stream.of(
                //normal castling rules should still apply
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
                //special cases for Lewis castling
                getWhiteLeftCastlingTwoSquaresApartInitialPositionArguments(),
                getWhiteRightCastlingTwoSquaresApartInitialPositionArguments(),
                getWhiteNotLeftCastlingTwoSquaresApartBlockedInitialPositionArguments(),
                getWhiteNotRightCastlingTwoSquaresApartBlockedInitialPositionArguments(),
                getWhiteLeftCastlingJumpInitialPositionArguments(),
                getWhiteRightCastlingJumpInitialPositionArguments(),
                getWhiteNotLeftCastlingTwoSquaresApartThreatenedPathInitialPositionArguments(),
                getWhiteNotRightCastlingTwoSquaresApartThreatenedPathInitialPositionArguments(),
                getWhiteLeftCastlingSwapInitialPositionArguments(),
                getWhiteRightCastlingSwapInitialPositionArguments(),
                getWhiteNotRightCastlingSwapInitialPositionArguments(),
                getWhiteNotLeftCastlingSwapInitialPositionArguments()
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

    //Special cases for Lewis/Orthodoxed castling
    private static Arguments getWhiteLeftCastlingTwoSquaresApartInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("c1")), PositionTranslate.getIndexByAlgebraicPosition("a1"), true);
    }

    private static Arguments getWhiteRightCastlingTwoSquaresApartInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("b1")), PositionTranslate.getIndexByAlgebraicPosition("d1"), true);
    }

    private static Arguments getWhiteNotLeftCastlingTwoSquaresApartBlockedInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.Bishop, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("c1")), PositionTranslate.getIndexByAlgebraicPosition("a1"), false);
    }

    private static Arguments getWhiteNotRightCastlingTwoSquaresApartBlockedInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.Knight, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("b1")), PositionTranslate.getIndexByAlgebraicPosition("d1"), false);
    }


    private static Arguments getWhiteLeftCastlingJumpInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("d1")), PositionTranslate.getIndexByAlgebraicPosition("b1"), true);
    }

    private static Arguments getWhiteRightCastlingJumpInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("d1")), PositionTranslate.getIndexByAlgebraicPosition("f1"), true);
    }

    private static Arguments getWhiteNotLeftCastlingTwoSquaresApartThreatenedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b8"), PieceTypeEnum.Queen, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("c1")), PositionTranslate.getIndexByAlgebraicPosition("a1"), false);
    }

    private static Arguments getWhiteNotRightCastlingTwoSquaresApartThreatenedPathInitialPositionArguments(){
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c8"), PieceTypeEnum.Rook, TeamEnum.Black, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("d1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("b1")), PositionTranslate.getIndexByAlgebraicPosition("d1"), false);
    }

    private static Arguments getWhiteLeftCastlingSwapInitialPositionArguments(){
        //Test castling with adjacent rook when rook's in the corner
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("a1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("b1")), PositionTranslate.getIndexByAlgebraicPosition("a1"), true);
    }

    private static Arguments getWhiteRightCastlingSwapInitialPositionArguments(){
        //Test castling with adjacent rook when rook's in the corner
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("g8"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("h8"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e1"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("g8")), PositionTranslate.getIndexByAlgebraicPosition("h8"), true);
    }

    private static Arguments getWhiteNotRightCastlingSwapInitialPositionArguments(){
        //Shouldn't be able to swap if the rook is not in the corner
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("b1")), PositionTranslate.getIndexByAlgebraicPosition("c1"), false);
    }

    private static Arguments getWhiteNotLeftCastlingSwapInitialPositionArguments(){
        //Shouldn't be able to swap if the rook is not in the corner
        CustomPosition initialPosition = new CustomPosition(TeamEnum.White);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("c1"), PieceTypeEnum.King, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("b1"), PieceTypeEnum.Rook, TeamEnum.White, false);
        initialPosition.addPiece(PositionTranslate.getIndexByAlgebraicPosition("e8"), PieceTypeEnum.King, TeamEnum.Black, false);
        return Arguments.of(initialPosition, initialPosition.getByPosition(PositionTranslate.getIndexByAlgebraicPosition("c1")), PositionTranslate.getIndexByAlgebraicPosition("b1"), false);
    }
}