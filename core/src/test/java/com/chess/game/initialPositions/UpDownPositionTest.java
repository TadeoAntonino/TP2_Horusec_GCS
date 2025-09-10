package com.chess.game.initialPositions;


import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
public class UpDownPositionTest {


    @ParameterizedTest(name = "Validate Position {0} Piece expected {2} {1}")
    @MethodSource("provideArgumentsOf_getByPositionTest")
    void getPieceByPosition(int position, PieceTypeEnum expectedPieceType, TeamEnum expectedPieceTeam) {
        //Arrange
        UpDownPosition positionUnderTest = new UpDownPosition ();

        //Act
        Piece actual =  positionUnderTest.getByPosition(position);

        //Assert

        Assertions.assertEquals(expectedPieceType, actual.getPieceType());
        Assertions.assertEquals(expectedPieceTeam, actual.getTeam());
    }

    private static Stream<Arguments> provideArgumentsOf_getByPositionTest() {
        return Stream.of(
                Arguments.of(0, PieceTypeEnum.Rook, TeamEnum.Black),
                Arguments.of(1, PieceTypeEnum.Knight, TeamEnum.Black),
                Arguments.of(2, PieceTypeEnum.Bishop, TeamEnum.Black),
                Arguments.of(3, PieceTypeEnum.Queen, TeamEnum.Black),
                Arguments.of(4, PieceTypeEnum.King, TeamEnum.Black),
                Arguments.of(5, PieceTypeEnum.Bishop, TeamEnum.Black),
                Arguments.of(6, PieceTypeEnum.Knight, TeamEnum.Black),
                Arguments.of(7, PieceTypeEnum.Rook, TeamEnum.Black),
                Arguments.of(8, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(9, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(10, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(11, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(12, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(13, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(14, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(15, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(63, PieceTypeEnum.Rook, TeamEnum.White),
                Arguments.of(62, PieceTypeEnum.Knight, TeamEnum.White),
                Arguments.of(61, PieceTypeEnum.Bishop, TeamEnum.White),
                Arguments.of(60, PieceTypeEnum.King, TeamEnum.White),
                Arguments.of(59, PieceTypeEnum.Queen, TeamEnum.White),
                Arguments.of(58, PieceTypeEnum.Bishop, TeamEnum.White),
                Arguments.of(57, PieceTypeEnum.Knight, TeamEnum.White),
                Arguments.of(56, PieceTypeEnum.Rook, TeamEnum.White),
                Arguments.of(55, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(54, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(53, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(52, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(51, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(50, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(49, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(48, PieceTypeEnum.Pawn, TeamEnum.White)
        );
    }

    @ParameterizedTest(name = "Validate Position {0} Piece expected {2} {1}")
    @MethodSource("provideArgumentsOf_getNullPieceByPosition")
    void getNullPieceByPosition(int position) {
        //Arrange
        UpDownPosition  positionUnderTest = new UpDownPosition ();

        //Act
        Piece actual =  positionUnderTest.getByPosition(position);

        //Assert
        Assertions.assertNull(actual);
    }
    private static Stream<Integer> provideArgumentsOf_getNullPieceByPosition() {
        return Stream.iterate(16, x -> x + 1)
                .limit(32);
    }

    @Test
    void getInitialTeam() {
        //Arrange
        UpDownPosition  positionUnderTest = new UpDownPosition ();

        //Act
        TeamEnum actual =  positionUnderTest.getInitialTeam();

        //Assert

        Assertions.assertEquals(TeamEnum.White, actual);
    }
}
