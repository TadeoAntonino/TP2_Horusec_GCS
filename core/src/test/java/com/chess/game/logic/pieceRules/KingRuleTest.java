package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.utils.PositionTranslate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class KingRuleTest {
    @ParameterizedTest(name = "Validate isMoved false for new {0} King")
    @MethodSource("provideArgumentsOf_initialIsMovedTest")
    void initialIsMovedTest(TeamEnum team) {
        //Arrange
        KingRule pieceRuleUnderTest = new KingRule(team);
        //Act
        boolean actual = pieceRuleUnderTest.isMoved;
        //Assert
        Assertions.assertFalse(actual);
    }

    private static Stream<Arguments> provideArgumentsOf_initialIsMovedTest() {
        return Stream.of(
                Arguments.of(TeamEnum.White),
                Arguments.of(TeamEnum.Black)
        );
    }

    @ParameterizedTest(name = "Validate isMoved true for moved {0} King")
    @MethodSource("provideArgumentsOf_initialIsMovedTrueTest")
    void initialIsMovedTrueTest(TeamEnum team) {
        //Arrange
        KingRule pieceRuleUnderTest = new KingRule(team);
        pieceRuleUnderTest.setMoved();
        //Act
        boolean actual = pieceRuleUnderTest.isMoved;
        //Assert
        Assertions.assertTrue(actual);
    }

    private static Stream<Arguments> provideArgumentsOf_initialIsMovedTrueTest() {
        return Stream.of(
                Arguments.of(TeamEnum.White),
                Arguments.of(TeamEnum.Black)
        );
    }

    @ParameterizedTest(name = "Validate movement paths for {0} King in position {1}")
    @MethodSource("provideArgumentsOf_getNonCastlingMovementPathsTest")
    void getNonCastlingMovementPathsTest(TeamEnum team, int position, ArrayList<MovementPath> expectedMovementPaths) {
        //Arrange
        KingRule pieceRuleUnderTest = new KingRule(team);
        pieceRuleUnderTest.setMoved();
        //Act
        ArrayList<MovementPath> actual = pieceRuleUnderTest.getMovementPaths(position);
        //Assert
        Assertions.assertEquals(expectedMovementPaths.size(), actual.size());
        for (MovementPath expectedMovementPath : expectedMovementPaths) {
            int index = expectedMovementPaths.indexOf(expectedMovementPath);
            MovementPath actualMovementPath = actual.get(index);
            Assertions.assertEquals(expectedMovementPath.getMoves().size(), actualMovementPath.getMoves().size());
            Assertions.assertEquals(expectedMovementPath.getMovementType(), actualMovementPath.getMovementType());
            assertThat(expectedMovementPath.getMoves(), is(equalTo(actualMovementPath.getMoves())));
        }
    }

    private static Stream<Arguments> provideArgumentsOf_getNonCastlingMovementPathsTest() {

        return Stream.of(
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("a1"), get_a1Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("h1"), get_h1Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("a8"), get_a8Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("h8"), get_h8Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("d4"), get_d4Position_WhiteExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("a1"), get_a1Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("h1"), get_h1Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("a8"), get_a8Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("h8"), get_h8Position_ExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("d4"), get_d4Position_BlackExpectedMovementPaths())
        );
    }

    private static ArrayList<MovementPath> get_a1Position_ExpectedMovementPaths() {
        MovementPath a1WhiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathRow.addMovement(0, 1);
        MovementPath a1WhiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathDiagonal.addMovement(1, 1);
        MovementPath a1WhiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathColumn.addMovement(1, 0);
        return new ArrayList<MovementPath>() {
            {
                add(a1WhiteMovementPathRow);
                add(a1WhiteMovementPathColumn);
                add(a1WhiteMovementPathDiagonal);
            }
        };
    }

    private static ArrayList<MovementPath> get_h1Position_ExpectedMovementPaths() {
        MovementPath whiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRow.addMovement(0, 6);
        MovementPath whiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonal.addMovement(1, 6);
        MovementPath whiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumn.addMovement(1, 7);
        return new ArrayList<MovementPath>() {
            {
                add(whiteMovementPathRow);
                add(whiteMovementPathColumn);
                add(whiteMovementPathDiagonal);
            }
        };
    }

    private static ArrayList<MovementPath> get_a8Position_ExpectedMovementPaths() {
        MovementPath a1WhiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathRow.addMovement(7, 1);
        MovementPath a1WhiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathDiagonal.addMovement(6, 1);
        MovementPath a1WhiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathColumn.addMovement(6, 0);
        return new ArrayList<MovementPath>() {
            {
                add(a1WhiteMovementPathRow);
                add(a1WhiteMovementPathColumn);
                add(a1WhiteMovementPathDiagonal);
            }
        };
    }

    private static ArrayList<MovementPath> get_h8Position_ExpectedMovementPaths() {
        MovementPath whiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRow.addMovement(7, 6);
        MovementPath whiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonal.addMovement(6, 6);
        MovementPath whiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumn.addMovement(6, 7);
        return new ArrayList<MovementPath>() {
            {
                add(whiteMovementPathRow);
                add(whiteMovementPathColumn);
                add(whiteMovementPathDiagonal);
            }
        };
    }

    private static ArrayList<MovementPath> get_d4Position_WhiteExpectedMovementPaths() {
        MovementPath whiteMovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowLeft.addMovement(3, 2);
        MovementPath whiteMovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowRight.addMovement(3, 4);
        MovementPath whiteMovementPathDiagonalLeftDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftDown.addMovement(2, 2);
        MovementPath whiteMovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftUp.addMovement(4, 2);
        MovementPath whiteMovementPathDiagonalRightDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightDown.addMovement(2, 4);
        MovementPath whiteMovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightUp.addMovement(4, 4);
        MovementPath whiteMovementPathColumnDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnDown.addMovement(2, 3);
        MovementPath whiteMovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnUp.addMovement(4, 3);
        return new ArrayList<MovementPath>() {
            {
                add(whiteMovementPathRowLeft);
                add(whiteMovementPathRowRight);
                add(whiteMovementPathColumnUp);
                add(whiteMovementPathColumnDown);
                add(whiteMovementPathDiagonalRightUp);
                add(whiteMovementPathDiagonalRightDown);
                add(whiteMovementPathDiagonalLeftUp);
                add(whiteMovementPathDiagonalLeftDown);
            }
        };
    }

    private static ArrayList<MovementPath> get_d4Position_BlackExpectedMovementPaths() {
        MovementPath whiteMovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowLeft.addMovement(3, 2);
        MovementPath whiteMovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowRight.addMovement(3, 4);
        MovementPath whiteMovementPathDiagonalLeftDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftDown.addMovement(2, 2);
        MovementPath whiteMovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftUp.addMovement(4, 2);
        MovementPath whiteMovementPathDiagonalRightDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightDown.addMovement(2, 4);
        MovementPath whiteMovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightUp.addMovement(4, 4);
        MovementPath whiteMovementPathColumnDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnDown.addMovement(2, 3);
        MovementPath whiteMovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnUp.addMovement(4, 3);
        return new ArrayList<MovementPath>() {
            {
                add(whiteMovementPathRowRight);
                add(whiteMovementPathRowLeft);
                add(whiteMovementPathColumnDown);
                add(whiteMovementPathColumnUp);
                add(whiteMovementPathDiagonalLeftDown);
                add(whiteMovementPathDiagonalLeftUp);
                add(whiteMovementPathDiagonalRightDown);
                add(whiteMovementPathDiagonalRightUp);
            }
        };
    }

    @ParameterizedTest(name = "Validate movement paths for {0} King in position {1}")
    @MethodSource("provideArgumentsOf_getCastlingMovementPathsTest")
    void getCastlingMovementPathsTest(TeamEnum team, int position, ArrayList<MovementPath> expectedMovementPaths) {
        //Arrange
        KingRule pieceRuleUnderTest = new KingRule(team);
        //Act
        ArrayList<MovementPath> actual = pieceRuleUnderTest.getMovementPaths(position);
        //Assert
        Assertions.assertEquals(actual.size(), expectedMovementPaths.size());
        for (MovementPath expectedMovementPath : expectedMovementPaths) {
            int index = expectedMovementPaths.indexOf(expectedMovementPath);
            MovementPath actualMovementPath = actual.get(index);
            Assertions.assertEquals(expectedMovementPath.getMoves().size(), actualMovementPath.getMoves().size());
            Assertions.assertEquals(expectedMovementPath.getMovementType(), actualMovementPath.getMovementType());
            assertThat(expectedMovementPath.getMoves(), is(equalTo(actualMovementPath.getMoves())));
        }
    }

    private static Stream<Arguments> provideArgumentsOf_getCastlingMovementPathsTest() {

        return Stream.of(
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("e1"), get_e1Position_WhiteExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("b1"), get_b1Position_WhiteExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("a1"), get_a1Position_WhiteExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("g1"), get_g1Position_WhiteExpectedMovementPaths()),
                Arguments.of(TeamEnum.White, PositionTranslate.getIndexByAlgebraicPosition("h1"), get_h1Position_WhiteExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("e8"), get_e8Position_BlackExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("g8"), get_g8Position_BlackExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("h8"), get_h8Position_BlackExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("a8"), get_a8Position_BlackExpectedMovementPaths()),
                Arguments.of(TeamEnum.Black, PositionTranslate.getIndexByAlgebraicPosition("b8"), get_b8Position_BlackExpectedMovementPaths())
        );
    }

    private static ArrayList<MovementPath> get_e1Position_WhiteExpectedMovementPaths() {
        MovementPath MovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathRowLeft.addMovement(0, 3);
        MovementPath MovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRowRight.addMovement(0, 5);
        MovementPath MovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalLeftUp.addMovement(1, 3);
        MovementPath MovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalRightUp.addMovement(1, 5);
        MovementPath MovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        MovementPathColumnUp.addMovement(1, 4);

        MovementPath whiteKingsideCastling = new MovementPath(MovementType.CASTLING);
        whiteKingsideCastling.addMovement(0, 5);
        whiteKingsideCastling.addMovement(0, 6);
        MovementPath whiteQueensideCastling = new MovementPath(MovementType.CASTLING);
        whiteQueensideCastling.addMovement(0, 3);
        whiteQueensideCastling.addMovement(0, 2);
        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRowLeft);
                add(MovementPathRowRight);
                add(MovementPathColumnUp);
                add(MovementPathDiagonalRightUp);
                add(MovementPathDiagonalLeftUp);
                add(whiteKingsideCastling);
                add(whiteQueensideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_e8Position_BlackExpectedMovementPaths() {
        MovementPath MovementPathLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathLeft.addMovement(7, 3);
        MovementPath MovementPathRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRight.addMovement(7, 5);
        MovementPath MovementPathLeftDown = new MovementPath(MovementType.CONTINUES);
        MovementPathLeftDown.addMovement(6, 3);
        MovementPath MovementPathDown = new MovementPath(MovementType.CONTINUES);
        MovementPathDown.addMovement(6, 4);
        MovementPath MovementPathRightDown = new MovementPath(MovementType.CONTINUES);
        MovementPathRightDown.addMovement(6, 5);

        MovementPath blackQueensideCastling = new MovementPath(MovementType.CASTLING);
        blackQueensideCastling.addMovement(7, 3);
        blackQueensideCastling.addMovement(7, 2);
        MovementPath blackKingsideCastling = new MovementPath(MovementType.CASTLING);
        blackKingsideCastling.addMovement(7, 5);
        blackKingsideCastling.addMovement(7, 6);
        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRight);
                add(MovementPathLeft);
                add(MovementPathDown);
                add(MovementPathLeftDown);
                add(MovementPathRightDown);
                add(blackQueensideCastling);
                add(blackKingsideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_b1Position_WhiteExpectedMovementPaths() {
        MovementPath MovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathRowLeft.addMovement(0, 0);
        MovementPath MovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRowRight.addMovement(0, 2);
        MovementPath MovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalLeftUp.addMovement(1, 0);
        MovementPath MovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalRightUp.addMovement(1, 2);
        MovementPath MovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        MovementPathColumnUp.addMovement(1, 1);

        MovementPath whiteKingsideCastling = new MovementPath(MovementType.CASTLING);
        whiteKingsideCastling.addMovement(0, 2);
        whiteKingsideCastling.addMovement(0, 3);
        MovementPath whiteQueensideCastling = new MovementPath(MovementType.CASTLING);
        whiteQueensideCastling.addMovement(0, 0);
        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRowLeft);
                add(MovementPathRowRight);
                add(MovementPathColumnUp);
                add(MovementPathDiagonalRightUp);
                add(MovementPathDiagonalLeftUp);
                add(whiteKingsideCastling);
                add(whiteQueensideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_a1Position_WhiteExpectedMovementPaths() {
        MovementPath MovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRowRight.addMovement(0, 1);
        MovementPath MovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalRightUp.addMovement(1, 1);
        MovementPath MovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        MovementPathColumnUp.addMovement(1, 0);

        MovementPath whiteKingsideCastling = new MovementPath(MovementType.CASTLING);
        whiteKingsideCastling.addMovement(0, 1);
        whiteKingsideCastling.addMovement(0, 2);
        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRowRight);
                add(MovementPathColumnUp);
                add(MovementPathDiagonalRightUp);
                add(whiteKingsideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_g1Position_WhiteExpectedMovementPaths() {
        MovementPath MovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathRowLeft.addMovement(0, 5);
        MovementPath MovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRowRight.addMovement(0, 7);
        MovementPath MovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalLeftUp.addMovement(1, 5);
        MovementPath MovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalRightUp.addMovement(1, 7);
        MovementPath MovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        MovementPathColumnUp.addMovement(1, 6);

        MovementPath whiteKingsideCastling = new MovementPath(MovementType.CASTLING);
        whiteKingsideCastling.addMovement(0, 7);
        MovementPath whiteQueensideCastling = new MovementPath(MovementType.CASTLING);
        whiteQueensideCastling.addMovement(0, 5);
        whiteQueensideCastling.addMovement(0, 4);
        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRowLeft);
                add(MovementPathRowRight);
                add(MovementPathColumnUp);
                add(MovementPathDiagonalRightUp);
                add(MovementPathDiagonalLeftUp);
                add(whiteKingsideCastling);
                add(whiteQueensideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_h1Position_WhiteExpectedMovementPaths() {
        MovementPath MovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathRowLeft.addMovement(0, 6);
        MovementPath MovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        MovementPathDiagonalLeftUp.addMovement(1, 6);
        MovementPath MovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        MovementPathColumnUp.addMovement(1, 7);

        MovementPath whiteKingsideCastling = new MovementPath(MovementType.CASTLING);
        whiteKingsideCastling.addMovement(0, 6);
        whiteKingsideCastling.addMovement(0, 5);
        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRowLeft);
                add(MovementPathColumnUp);
                add(MovementPathDiagonalLeftUp);
                add(whiteKingsideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_g8Position_BlackExpectedMovementPaths() {
        MovementPath MovementPathLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathLeft.addMovement(7, 5);
        MovementPath MovementPathRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRight.addMovement(7, 7);
        MovementPath MovementPathLeftDown = new MovementPath(MovementType.CONTINUES);
        MovementPathLeftDown.addMovement(6, 5);
        MovementPath MovementPathDown = new MovementPath(MovementType.CONTINUES);
        MovementPathDown.addMovement(6, 6);
        MovementPath MovementPathRightDown = new MovementPath(MovementType.CONTINUES);
        MovementPathRightDown.addMovement(6, 7);

        MovementPath blackQueensideCastling = new MovementPath(MovementType.CASTLING);
        blackQueensideCastling.addMovement(7, 5);
        blackQueensideCastling.addMovement(7, 4);
        MovementPath blackKingsideCastling = new MovementPath(MovementType.CASTLING);
        blackKingsideCastling.addMovement(7, 7);

        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRight);
                add(MovementPathLeft);
                add(MovementPathDown);
                add(MovementPathLeftDown);
                add(MovementPathRightDown);
                add(blackQueensideCastling);
                add(blackKingsideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_h8Position_BlackExpectedMovementPaths() {
        MovementPath MovementPathLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathLeft.addMovement(7, 6);
        MovementPath MovementPathLeftDown = new MovementPath(MovementType.CONTINUES);
        MovementPathLeftDown.addMovement(6, 6);
        MovementPath MovementPathDown = new MovementPath(MovementType.CONTINUES);
        MovementPathDown.addMovement(6, 7);

        MovementPath blackQueensideCastling = new MovementPath(MovementType.CASTLING);
        blackQueensideCastling.addMovement(7, 6);
        blackQueensideCastling.addMovement(7, 5);

        return new ArrayList<MovementPath>() {
            {
                add(MovementPathLeft);
                add(MovementPathDown);
                add(MovementPathLeftDown);
                add(blackQueensideCastling);
            }
        };
    }
    private static ArrayList<MovementPath> get_b8Position_BlackExpectedMovementPaths() {
        MovementPath MovementPathLeft = new MovementPath(MovementType.CONTINUES);
        MovementPathLeft.addMovement(7, 0);
        MovementPath MovementPathRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRight.addMovement(7, 2);
        MovementPath MovementPathLeftDown = new MovementPath(MovementType.CONTINUES);
        MovementPathLeftDown.addMovement(6, 0);
        MovementPath MovementPathDown = new MovementPath(MovementType.CONTINUES);
        MovementPathDown.addMovement(6, 1);
        MovementPath MovementPathRightDown = new MovementPath(MovementType.CONTINUES);
        MovementPathRightDown.addMovement(6, 2);

        MovementPath blackQueensideCastling = new MovementPath(MovementType.CASTLING);
        blackQueensideCastling.addMovement(7, 0);
        MovementPath blackKingsideCastling = new MovementPath(MovementType.CASTLING);
        blackKingsideCastling.addMovement(7, 2);
        blackKingsideCastling.addMovement(7, 3);

        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRight);
                add(MovementPathLeft);
                add(MovementPathDown);
                add(MovementPathLeftDown);
                add(MovementPathRightDown);
                add(blackQueensideCastling);
                add(blackKingsideCastling);
            }

        };
    }

    private static ArrayList<MovementPath> get_a8Position_BlackExpectedMovementPaths() {
        MovementPath MovementPathRight = new MovementPath(MovementType.CONTINUES);
        MovementPathRight.addMovement(7, 1);
        MovementPath MovementPathRightDown = new MovementPath(MovementType.CONTINUES);
        MovementPathRightDown.addMovement(6, 1);
        MovementPath MovementPathDown = new MovementPath(MovementType.CONTINUES);
        MovementPathDown.addMovement(6, 0);

        MovementPath blackKingsideCastling = new MovementPath(MovementType.CASTLING);
        blackKingsideCastling.addMovement(7, 1);
        blackKingsideCastling.addMovement(7, 2);

        return new ArrayList<MovementPath>() {
            {
                add(MovementPathRight);
                add(MovementPathDown);
                add(MovementPathRightDown);
                add(blackKingsideCastling);
            }
        };
    }
}