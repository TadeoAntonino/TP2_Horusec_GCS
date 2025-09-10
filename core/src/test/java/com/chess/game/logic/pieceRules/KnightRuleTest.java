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

class KnightRuleTest {
    @ParameterizedTest(name = "Validate isMoved false for new {0} Knight")
    @MethodSource("provideArgumentsOf_initialIsMovedTest")
    void initialIsMovedTest(TeamEnum team) {
        //Arrange
        KnightRule pieceRuleUnderTest = new KnightRule(team);
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

    @ParameterizedTest(name = "Validate isMoved true for moved {0} Knight")
    @MethodSource("provideArgumentsOf_initialIsMovedTrueTest")
    void initialIsMovedTrueTest(TeamEnum team) {
        //Arrange
        KnightRule pieceRuleUnderTest = new KnightRule(team);
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
    @ParameterizedTest(name = "Validate movement paths for {0} Knight in position {1}")
    @MethodSource("provideArgumentsOf_getMovementPathsTest")
    void getMovementPathsTest(TeamEnum team, int position, ArrayList<MovementPath> expectedMovementPaths) {
        //Arrange
        KnightRule pieceRuleUnderTest = new KnightRule(team);
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
        MovementPath b3Jump = new MovementPath(MovementType.JUMP);
        b3Jump.addMovement(2,1);
        MovementPath c2Jump = new MovementPath(MovementType.JUMP);
        c2Jump.addMovement(1,2);
        return new ArrayList<MovementPath>() {
            {
                add(b3Jump);
                add(c2Jump);
            }
        };
    }
    private static ArrayList<MovementPath> get_h1Position_ExpectedMovementPaths(){
        MovementPath g3Jump = new MovementPath(MovementType.JUMP);
        g3Jump.addMovement(2,6);
        MovementPath f2Jump = new MovementPath(MovementType.JUMP);
        f2Jump.addMovement(1,5);
        return new ArrayList<MovementPath>() {
            {
                add(g3Jump);
                add(f2Jump);
            }
        };
    }
    private static ArrayList<MovementPath> get_a8Position_ExpectedMovementPaths(){
        MovementPath b6Jump = new MovementPath(MovementType.JUMP);
        b6Jump.addMovement(5,1);
        MovementPath c7Jump = new MovementPath(MovementType.JUMP);
        c7Jump.addMovement(6,2);
        return new ArrayList<MovementPath>() {
            {
                add(b6Jump);
                add(c7Jump);
            }
        };
    }
    private static ArrayList<MovementPath> get_h8Position_ExpectedMovementPaths(){
        MovementPath g6Jump = new MovementPath(MovementType.JUMP);
        g6Jump.addMovement(5,6);
        MovementPath f7Jump = new MovementPath(MovementType.JUMP);
        f7Jump.addMovement(6,5);
        return new ArrayList<MovementPath>() {
            {
                add(g6Jump);
                add(f7Jump);
            }
        };
    }
    private static ArrayList<MovementPath> get_d4Position_WhiteExpectedMovementPaths(){
        MovementPath c6Jump = new MovementPath(MovementType.JUMP);
        c6Jump.addMovement(5,2);
        MovementPath e6Jump = new MovementPath(MovementType.JUMP);
        e6Jump.addMovement(5,4);

        MovementPath b5Jump = new MovementPath(MovementType.JUMP);
        b5Jump.addMovement(4,1);
        MovementPath b3Jump = new MovementPath(MovementType.JUMP);
        b3Jump.addMovement(2,1);

        MovementPath c2Jump = new MovementPath(MovementType.JUMP);
        c2Jump.addMovement(1,2);
        MovementPath e2Jump = new MovementPath(MovementType.JUMP);
        e2Jump.addMovement(1,4);

        MovementPath f5Jump = new MovementPath(MovementType.JUMP);
        f5Jump.addMovement(4,5);
        MovementPath f3Jump = new MovementPath(MovementType.JUMP);
        f3Jump.addMovement(2,5);
        return new ArrayList<MovementPath>() {
            {
                add(c2Jump);
                add(c6Jump);
                add(b3Jump);
                add(b5Jump);
                add(e2Jump);
                add(e6Jump);
                add(f3Jump);
                add(f5Jump);
            }
        };
    }
    private static ArrayList<MovementPath> get_d4Position_BlackExpectedMovementPaths(){
        MovementPath c6Jump = new MovementPath(MovementType.JUMP);
        c6Jump.addMovement(5,2);
        MovementPath e6Jump = new MovementPath(MovementType.JUMP);
        e6Jump.addMovement(5,4);

        MovementPath b5Jump = new MovementPath(MovementType.JUMP);
        b5Jump.addMovement(4,1);
        MovementPath b3Jump = new MovementPath(MovementType.JUMP);
        b3Jump.addMovement(2,1);

        MovementPath c2Jump = new MovementPath(MovementType.JUMP);
        c2Jump.addMovement(1,2);
        MovementPath e2Jump = new MovementPath(MovementType.JUMP);
        e2Jump.addMovement(1,4);

        MovementPath f5Jump = new MovementPath(MovementType.JUMP);
        f5Jump.addMovement(4,5);
        MovementPath f3Jump = new MovementPath(MovementType.JUMP);
        f3Jump.addMovement(2,5);
        return new ArrayList<MovementPath>() {
            {
                add(e6Jump);
                add(e2Jump);
                add(f5Jump);
                add(f3Jump);
                add(c6Jump);
                add(c2Jump);
                add(b5Jump);
                add(b3Jump);
            }
        };
    }
}
