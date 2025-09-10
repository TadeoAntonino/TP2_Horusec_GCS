package com.chess.game.logic.pieceRules;

import com.chess.game.logic.enums.TeamEnum;
import com.chess.game.utils.PositionTranslate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.stream.Stream;

class QueenRuleTest {

    @ParameterizedTest(name = "Validate isMoved false for new {0} Queen")
    @MethodSource("provideArgumentsOf_initialIsMovedTest")
    void initialIsMovedTest(TeamEnum team) {
        //Arrange
        QueenRule pieceRuleUnderTest = new QueenRule(team);
        //Act
        boolean actual =  pieceRuleUnderTest.isMoved;
        //Assert
        Assertions.assertFalse(actual);
    }

    private static Stream<Arguments> provideArgumentsOf_initialIsMovedTest() {
        return Stream.of(
                Arguments.of( TeamEnum.White),
                Arguments.of( TeamEnum.Black)
        );
    }

    @ParameterizedTest(name = "Validate isMoved true for moved {0} Queen")
    @MethodSource("provideArgumentsOf_initialIsMovedTrueTest")
    void initialIsMovedTrueTest(TeamEnum team) {
        //Arrange
        QueenRule pieceRuleUnderTest = new QueenRule(team);
        pieceRuleUnderTest.setMoved();
        //Act
        boolean actual =  pieceRuleUnderTest.isMoved;
        //Assert
        Assertions.assertTrue(actual);
    }

    private static Stream<Arguments> provideArgumentsOf_initialIsMovedTrueTest() {
        return Stream.of(
                Arguments.of( TeamEnum.White),
                Arguments.of( TeamEnum.Black)
        );
    }

    @ParameterizedTest(name = "Validate movement paths for {0} Queen in position {1}")
    @MethodSource("provideArgumentsOf_getMovementPathsTest")
    void getMovementPathsTest(TeamEnum team, int position, ArrayList<MovementPath> expectedMovementPaths) {
        //Arrange
        QueenRule pieceRuleUnderTest = new QueenRule(team);
        //Act
        ArrayList<MovementPath> actual =  pieceRuleUnderTest.getMovementPaths(position);
        //Assert
        Assertions.assertEquals(expectedMovementPaths.size(),actual.size());
        for(MovementPath expectedMovementPath : expectedMovementPaths){
            int index = expectedMovementPaths.indexOf(expectedMovementPath);
            MovementPath actualMovementPath = actual.get(index);
            Assertions.assertEquals(expectedMovementPath.getMoves().size(),actualMovementPath.getMoves().size());
            Assertions.assertEquals(expectedMovementPath.getMovementType(),actualMovementPath.getMovementType());
            assertThat(expectedMovementPath.getMoves(), is(equalTo(actualMovementPath.getMoves())));
        }
    }

    private static Stream<Arguments> provideArgumentsOf_getMovementPathsTest() {

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

    private static ArrayList<MovementPath> get_a1Position_ExpectedMovementPaths(){
        MovementPath a1WhiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathRow.addMovement(0,1);
        a1WhiteMovementPathRow.addMovement(0,2);
        a1WhiteMovementPathRow.addMovement(0,3);
        a1WhiteMovementPathRow.addMovement(0,4);
        a1WhiteMovementPathRow.addMovement(0,5);
        a1WhiteMovementPathRow.addMovement(0,6);
        a1WhiteMovementPathRow.addMovement(0,7);
        MovementPath a1WhiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathDiagonal.addMovement(1,1);
        a1WhiteMovementPathDiagonal.addMovement(2,2);
        a1WhiteMovementPathDiagonal.addMovement(3,3);
        a1WhiteMovementPathDiagonal.addMovement(4,4);
        a1WhiteMovementPathDiagonal.addMovement(5,5);
        a1WhiteMovementPathDiagonal.addMovement(6,6);
        a1WhiteMovementPathDiagonal.addMovement(7,7);
        MovementPath a1WhiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathColumn.addMovement(1,0);
        a1WhiteMovementPathColumn.addMovement(2,0);
        a1WhiteMovementPathColumn.addMovement(3,0);
        a1WhiteMovementPathColumn.addMovement(4,0);
        a1WhiteMovementPathColumn.addMovement(5,0);
        a1WhiteMovementPathColumn.addMovement(6,0);
        a1WhiteMovementPathColumn.addMovement(7,0);
        return new ArrayList<MovementPath>() {
            {
                add(a1WhiteMovementPathRow);
                add(a1WhiteMovementPathColumn);
                add(a1WhiteMovementPathDiagonal);
            }
        };
    }
    private static ArrayList<MovementPath> get_h1Position_ExpectedMovementPaths(){
        MovementPath whiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRow.addMovement(0,6);
        whiteMovementPathRow.addMovement(0,5);
        whiteMovementPathRow.addMovement(0,4);
        whiteMovementPathRow.addMovement(0,3);
        whiteMovementPathRow.addMovement(0,2);
        whiteMovementPathRow.addMovement(0,1);
        whiteMovementPathRow.addMovement(0,0);

        MovementPath whiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonal.addMovement(1,6);
        whiteMovementPathDiagonal.addMovement(2,5);
        whiteMovementPathDiagonal.addMovement(3,4);
        whiteMovementPathDiagonal.addMovement(4,3);
        whiteMovementPathDiagonal.addMovement(5,2);
        whiteMovementPathDiagonal.addMovement(6,1);
        whiteMovementPathDiagonal.addMovement(7,0);

        MovementPath whiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumn.addMovement(1,7);
        whiteMovementPathColumn.addMovement(2,7);
        whiteMovementPathColumn.addMovement(3,7);
        whiteMovementPathColumn.addMovement(4,7);
        whiteMovementPathColumn.addMovement(5,7);
        whiteMovementPathColumn.addMovement(6,7);
        whiteMovementPathColumn.addMovement(7,7);
        return new ArrayList<MovementPath>() {
            {
                add(whiteMovementPathRow);
                add(whiteMovementPathColumn);
                add(whiteMovementPathDiagonal);
            }
        };
    }
    private static ArrayList<MovementPath> get_a8Position_ExpectedMovementPaths(){
        MovementPath a1WhiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathRow.addMovement(7,1);
        a1WhiteMovementPathRow.addMovement(7,2);
        a1WhiteMovementPathRow.addMovement(7,3);
        a1WhiteMovementPathRow.addMovement(7,4);
        a1WhiteMovementPathRow.addMovement(7,5);
        a1WhiteMovementPathRow.addMovement(7,6);
        a1WhiteMovementPathRow.addMovement(7,7);
        MovementPath a1WhiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathDiagonal.addMovement(6,1);
        a1WhiteMovementPathDiagonal.addMovement(5,2);
        a1WhiteMovementPathDiagonal.addMovement(4,3);
        a1WhiteMovementPathDiagonal.addMovement(3,4);
        a1WhiteMovementPathDiagonal.addMovement(2,5);
        a1WhiteMovementPathDiagonal.addMovement(1,6);
        a1WhiteMovementPathDiagonal.addMovement(0,7);
        MovementPath a1WhiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        a1WhiteMovementPathColumn.addMovement(6,0);
        a1WhiteMovementPathColumn.addMovement(5,0);
        a1WhiteMovementPathColumn.addMovement(4,0);
        a1WhiteMovementPathColumn.addMovement(3,0);
        a1WhiteMovementPathColumn.addMovement(2,0);
        a1WhiteMovementPathColumn.addMovement(1,0);
        a1WhiteMovementPathColumn.addMovement(0,0);
        return new ArrayList<MovementPath>() {
            {
                add(a1WhiteMovementPathRow);
                add(a1WhiteMovementPathColumn);
                add(a1WhiteMovementPathDiagonal);
            }
        };
    }
    private static ArrayList<MovementPath> get_h8Position_ExpectedMovementPaths(){
        MovementPath whiteMovementPathRow = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRow.addMovement(7,6);
        whiteMovementPathRow.addMovement(7,5);
        whiteMovementPathRow.addMovement(7,4);
        whiteMovementPathRow.addMovement(7,3);
        whiteMovementPathRow.addMovement(7,2);
        whiteMovementPathRow.addMovement(7,1);
        whiteMovementPathRow.addMovement(7,0);

        MovementPath whiteMovementPathDiagonal = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonal.addMovement(6,6);
        whiteMovementPathDiagonal.addMovement(5,5);
        whiteMovementPathDiagonal.addMovement(4,4);
        whiteMovementPathDiagonal.addMovement(3,3);
        whiteMovementPathDiagonal.addMovement(2,2);
        whiteMovementPathDiagonal.addMovement(1,1);
        whiteMovementPathDiagonal.addMovement(0,0);

        MovementPath whiteMovementPathColumn = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumn.addMovement(6,7);
        whiteMovementPathColumn.addMovement(5,7);
        whiteMovementPathColumn.addMovement(4,7);
        whiteMovementPathColumn.addMovement(3,7);
        whiteMovementPathColumn.addMovement(2,7);
        whiteMovementPathColumn.addMovement(1,7);
        whiteMovementPathColumn.addMovement(0,7);
        return new ArrayList<MovementPath>() {
            {
                add(whiteMovementPathRow);
                add(whiteMovementPathColumn);
                add(whiteMovementPathDiagonal);
            }
        };
    }
    private static ArrayList<MovementPath> get_d4Position_WhiteExpectedMovementPaths(){
        MovementPath whiteMovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowLeft.addMovement(3,2);
        whiteMovementPathRowLeft.addMovement(3,1);
        whiteMovementPathRowLeft.addMovement(3,0);
        MovementPath whiteMovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowRight.addMovement(3,4);
        whiteMovementPathRowRight.addMovement(3,5);
        whiteMovementPathRowRight.addMovement(3,6);
        whiteMovementPathRowRight.addMovement(3,7);
        MovementPath whiteMovementPathDiagonalLeftDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftDown.addMovement(2,2);
        whiteMovementPathDiagonalLeftDown.addMovement(1,1);
        whiteMovementPathDiagonalLeftDown.addMovement(0,0);
        MovementPath whiteMovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftUp.addMovement(4,2);
        whiteMovementPathDiagonalLeftUp.addMovement(5,1);
        whiteMovementPathDiagonalLeftUp.addMovement(6,0);
        MovementPath whiteMovementPathDiagonalRightDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightDown.addMovement(2,4);
        whiteMovementPathDiagonalRightDown.addMovement(1,5);
        whiteMovementPathDiagonalRightDown.addMovement(0,6);
        MovementPath whiteMovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightUp.addMovement(4,4);
        whiteMovementPathDiagonalRightUp.addMovement(5,5);
        whiteMovementPathDiagonalRightUp.addMovement(6,6);
        whiteMovementPathDiagonalRightUp.addMovement(7,7);
        MovementPath whiteMovementPathColumnDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnDown.addMovement(2,3);
        whiteMovementPathColumnDown.addMovement(1,3);
        whiteMovementPathColumnDown.addMovement(0,3);
        MovementPath whiteMovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnUp.addMovement(4,3);
        whiteMovementPathColumnUp.addMovement(5,3);
        whiteMovementPathColumnUp.addMovement(6,3);
        whiteMovementPathColumnUp.addMovement(7,3);
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
    private static ArrayList<MovementPath> get_d4Position_BlackExpectedMovementPaths(){
        MovementPath whiteMovementPathRowLeft = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowLeft.addMovement(3,2);
        whiteMovementPathRowLeft.addMovement(3,1);
        whiteMovementPathRowLeft.addMovement(3,0);
        MovementPath whiteMovementPathRowRight = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathRowRight.addMovement(3,4);
        whiteMovementPathRowRight.addMovement(3,5);
        whiteMovementPathRowRight.addMovement(3,6);
        whiteMovementPathRowRight.addMovement(3,7);
        MovementPath whiteMovementPathDiagonalLeftDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftDown.addMovement(2,2);
        whiteMovementPathDiagonalLeftDown.addMovement(1,1);
        whiteMovementPathDiagonalLeftDown.addMovement(0,0);
        MovementPath whiteMovementPathDiagonalLeftUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalLeftUp.addMovement(4,2);
        whiteMovementPathDiagonalLeftUp.addMovement(5,1);
        whiteMovementPathDiagonalLeftUp.addMovement(6,0);
        MovementPath whiteMovementPathDiagonalRightDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightDown.addMovement(2,4);
        whiteMovementPathDiagonalRightDown.addMovement(1,5);
        whiteMovementPathDiagonalRightDown.addMovement(0,6);
        MovementPath whiteMovementPathDiagonalRightUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathDiagonalRightUp.addMovement(4,4);
        whiteMovementPathDiagonalRightUp.addMovement(5,5);
        whiteMovementPathDiagonalRightUp.addMovement(6,6);
        whiteMovementPathDiagonalRightUp.addMovement(7,7);
        MovementPath whiteMovementPathColumnDown = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnDown.addMovement(2,3);
        whiteMovementPathColumnDown.addMovement(1,3);
        whiteMovementPathColumnDown.addMovement(0,3);
        MovementPath whiteMovementPathColumnUp = new MovementPath(MovementType.CONTINUES);
        whiteMovementPathColumnUp.addMovement(4,3);
        whiteMovementPathColumnUp.addMovement(5,3);
        whiteMovementPathColumnUp.addMovement(6,3);
        whiteMovementPathColumnUp.addMovement(7,3);
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
}
