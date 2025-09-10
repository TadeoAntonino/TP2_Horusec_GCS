package com.chess.game.initialPositions;

import com.chess.game.logic.Piece;
import com.chess.game.logic.enums.PieceTypeEnum;
import com.chess.game.logic.enums.TeamEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FischerPositionTest {
    private static FischerPosition positionTest;
    private static ArrayList<PieceTypeEnum> firstRowPieces;
    private static ArrayList<PieceTypeEnum> lastRowPieces;
    @BeforeAll
    static void initialize(){
        positionTest = new FischerPosition();

        firstRowPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            firstRowPieces.add(positionTest.getByPosition(i).getPieceType());
        }

        lastRowPieces = new ArrayList<>();
        for (int i = 56; i < 64; i++){
            lastRowPieces.add(positionTest.getByPosition(i).getPieceType());
        }
    }
    @Test
    void rookKingOrderTest(){
        assertTrue(firstRowPieces.indexOf(PieceTypeEnum.Rook) < firstRowPieces.indexOf(PieceTypeEnum.King));
        assertTrue(firstRowPieces.lastIndexOf(PieceTypeEnum.Rook) > firstRowPieces.indexOf(PieceTypeEnum.King));
    }
    @Test
    void bishopPositionTest(){
        assertEquals(1, (firstRowPieces.indexOf(PieceTypeEnum.Bishop) + firstRowPieces.lastIndexOf(PieceTypeEnum.Bishop)) % 2);
    }

    @ParameterizedTest(name = "Find Row Duplicates, expected {1} {0}")
    @MethodSource("provideArgumentsOf_firstRowDuplicatesTest")
    void firstRowDuplicatesTest(PieceTypeEnum pieceType, int expectedAmount){
        int pieceCount = (int)firstRowPieces.stream().filter(item -> item == pieceType).count();
        assertEquals(pieceCount, expectedAmount);
    }

    @ParameterizedTest(name = "Validate mirrored rows, positions {0} {1}")
    @MethodSource("provideArgumentsOf_rowsMirroredTest")
    void rowsMirroredTest(int whitePos, int blackPos){
        assertEquals(positionTest.getByPosition(whitePos).getPieceType(), positionTest.getByPosition(blackPos).getPieceType());
    }

    private static Stream<Arguments> provideArgumentsOf_rowsMirroredTest(){
        return Stream.of(
                Arguments.of(0,56),
                Arguments.of(1,57),
                Arguments.of(2,58),
                Arguments.of(3,59),
                Arguments.of(4,60),
                Arguments.of(5,61),
                Arguments.of(6,62),
                Arguments.of(7,63)
        );
    }

    private static Stream<Arguments> provideArgumentsOf_firstRowDuplicatesTest() {
        return Stream.of(
                Arguments.of(PieceTypeEnum.Rook, 2),
                Arguments.of(PieceTypeEnum.Knight, 2),
                Arguments.of(PieceTypeEnum.Bishop, 2),
                Arguments.of(PieceTypeEnum.Queen, 1),
                Arguments.of(PieceTypeEnum.King, 1)
        );
    }

    @ParameterizedTest(name = "Validate Position {0} Piece expected {2} {1}")
    @MethodSource("provideArgumentsOf_getByPositionTest")
    void getPieceByPosition(int position, PieceTypeEnum expectedPieceType, TeamEnum expectedPieceTeam) {
        Piece actual = positionTest.getByPosition(position);

//        assertEquals(expectedPieceType, actual.getPieceType());
        assertEquals(expectedPieceTeam, actual.getTeam());
    }

    private static Stream<Arguments> provideArgumentsOf_getByPositionTest() {
        return Stream.of(
                Arguments.of(0, PieceTypeEnum.Rook, TeamEnum.White),
                Arguments.of(1, PieceTypeEnum.Knight, TeamEnum.White),
                Arguments.of(2, PieceTypeEnum.Bishop, TeamEnum.White),
                Arguments.of(3, PieceTypeEnum.Queen, TeamEnum.White),
                Arguments.of(4, PieceTypeEnum.King, TeamEnum.White),
                Arguments.of(5, PieceTypeEnum.Bishop, TeamEnum.White),
                Arguments.of(6, PieceTypeEnum.Knight, TeamEnum.White),
                Arguments.of(7, PieceTypeEnum.Rook, TeamEnum.White),
                Arguments.of(8, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(9, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(10, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(11, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(12, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(13, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(14, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(15, PieceTypeEnum.Pawn, TeamEnum.White),
                Arguments.of(63, PieceTypeEnum.Rook, TeamEnum.Black),
                Arguments.of(62, PieceTypeEnum.Knight, TeamEnum.Black),
                Arguments.of(61, PieceTypeEnum.Bishop, TeamEnum.Black),
                Arguments.of(60, PieceTypeEnum.King, TeamEnum.Black),
                Arguments.of(59, PieceTypeEnum.Queen, TeamEnum.Black),
                Arguments.of(58, PieceTypeEnum.Bishop, TeamEnum.Black),
                Arguments.of(57, PieceTypeEnum.Knight, TeamEnum.Black),
                Arguments.of(56, PieceTypeEnum.Rook, TeamEnum.Black),
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

    @ParameterizedTest(name = "Validate Position {0} Piece expected {2} {1}")
    @MethodSource("provideArgumentsOf_getNullPieceByPosition")
    void getNullPieceByPosition(int position) {
       Piece actual = positionTest.getByPosition(position);
        //Assert
        assertNull(actual);
    }

    private static Stream<Integer> provideArgumentsOf_getNullPieceByPosition() {
        return Stream.iterate(16, x -> x + 1)
                .limit(32);
    }
}