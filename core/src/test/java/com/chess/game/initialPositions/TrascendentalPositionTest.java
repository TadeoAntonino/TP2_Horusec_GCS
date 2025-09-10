package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class TrascendentalPositionTest {

    @Test
    void getInitialTeam() {
        //Arrange
        TrascendentalPosition positionUnderTest = new TrascendentalPosition();

        //Act
        TeamEnum actual =  positionUnderTest.getInitialTeam();

        //Assert

        Assertions.assertEquals(TeamEnum.White, actual);
    }

    @ParameterizedTest(name = "Valida posiciones nulas")
    @MethodSource("provideArgumentsOf_getNullPieceByPosition")
    void getNullPieceByPosition(int position) {
        //Arrange
        TrascendentalPosition positionUnderTest = new TrascendentalPosition();

        //Act
        Piece actual =  positionUnderTest.getByPosition(position);

        //Assert
        Assertions.assertNull(actual);
    }
    private static Stream<Integer> provideArgumentsOf_getNullPieceByPosition() {
        return Stream.iterate(16, x -> x + 1)
                .limit(32);
    }

    @ParameterizedTest(name = "Valida las posiciones de los peones")
    @MethodSource("provideArgumentsOf_getPawnsPositionTest")
    void getPawnsByPosition(int position, PieceTypeEnum expectedPieceType, TeamEnum expectedPieceTeam) {
        //Arrange
        TrascendentalPosition positionUnderTest = new TrascendentalPosition();

        //Act
        Piece actual =  positionUnderTest.getByPosition(position);

        //Assert

        Assertions.assertEquals(expectedPieceType, actual.getPieceType());
        Assertions.assertEquals(expectedPieceTeam, actual.getTeam());
    }
    private static Stream<Arguments> provideArgumentsOf_getPawnsPositionTest() {
        return Stream.of(
                Arguments.of(10, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(11, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(12, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(13, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(14, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(15, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(55, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(54, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(53, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(52, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(51, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(50, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(49, PieceTypeEnum.Pawn, TeamEnum.Black),
                Arguments.of(48, PieceTypeEnum.Pawn, TeamEnum.Black)
        );
    }

    @Test
    void getPiecesCount(){

        TrascendentalPosition positionUnderTest = new TrascendentalPosition();

        HashMap<Integer,PieceTypeEnum> pieces = new HashMap<>();


        int queenCount = 0;
        int kingCount = 0;
        int rookCount = 0;
        int bishopCount = 0;
        int knightCount = 0;

        for(int i = 0; i<=7; i++){
            pieces.put(i,positionUnderTest.getByPosition(i).getPieceType());
            pieces.put(63-i,positionUnderTest.getByPosition(63-i).getPieceType());
        }

        for(int j = 0; j<=7; j++){
            PieceTypeEnum actual = pieces.get(j);
                switch(actual){
                    case Rook:{rookCount += 1; continue;}
                    case Queen:{queenCount += 1;continue;}
                    case King:{kingCount += 1;continue;}
                    case Bishop:{bishopCount += 1;continue;}
                    default: {knightCount += 1;}
            }
        }

        for(int k = 0; k<=7; k++) {
            PieceTypeEnum actual = pieces.get(63 - k);
                switch (actual) {
                    case Rook:{rookCount += 1; continue;}
                    case Queen:{queenCount += 1;continue;}
                    case King:{kingCount += 1;continue;}
                    case Bishop:{bishopCount += 1;continue;}
                    default: {knightCount += 1;}
                }
        }

        Assertions.assertEquals(2,queenCount);
        Assertions.assertEquals(2,kingCount);
        Assertions.assertEquals(4,knightCount);
        Assertions.assertEquals(4,rookCount);
        Assertions.assertEquals(4,bishopCount);
    }
}
