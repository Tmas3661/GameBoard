import org.junit.jupiter.api.*;
import org.progtech.setting.Connect4Game;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class Connect4GameTest {

    private Connect4Game game;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        game = new Connect4Game();
        // Set up to capture console output
        outputStream = new ByteArrayOutputStream();
        originalSystemOut = System.out; // Save original System.out
        System.setOut(new PrintStream(outputStream)); // Redirect output to capture it
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalSystemOut); // Restore original System.out
    }

    @Test
    void testInitializeBoard() {
        // Arrange
        game.initializeBoard();

        // Act
        char[][] board = game.getBoard();

        // Assert
        for (char[] row : board) {
            for (char cell : row) {
                assertEquals('.', cell, "All cells should be empty initially");
            }
        }
    }

    @Test
    void testValidMove() {
        // Act & Assert
        assertTrue(game.isValidMove(3), "Column 3 should be valid initially");
        assertFalse(game.isValidMove(-1), "Negative column should be invalid");
        assertFalse(game.isValidMove(8), "Out-of-range column should be invalid");
    }

    @Test
    void testMakeMove() {
        // Arrange
        int column = 3;
        char playerSymbol = 'Y';

        // Act
        game.makeMove(column, playerSymbol);

        // Assert
        char[][] board = game.getBoard();
        assertEquals(playerSymbol, board[5][column], "Player's symbol should occupy the bottom-most row of the column");
    }

    @Test
    void testWinConditionHorizontal() {
        // Arrange
        char[][] board = game.getBoard();
        board[5][0] = 'Y';
        board[5][1] = 'Y';
        board[5][2] = 'Y';
        board[5][3] = 'Y';

        // Act
        boolean isWin = game.checkWin();

        // Assert
        assertTrue(isWin, "Horizontal win condition should be met");
    }

    @Test
    void testWinConditionVertical() {
        // Arrange
        char[][] board = game.getBoard();
        board[2][0] = 'Y';
        board[3][0] = 'Y';
        board[4][0] = 'Y';
        board[5][0] = 'Y';

        // Act
        boolean isWin = game.checkWin();

        // Assert
        assertTrue(isWin, "Vertical win condition should be met");
    }

    @Test
    void testWinConditionDiagonal() {
        // Arrange
        char[][] board = game.getBoard();
        board[5][0] = 'Y';
        board[4][1] = 'Y';
        board[3][2] = 'Y';
        board[2][3] = 'Y';

        // Act
        boolean isWin = game.checkWin();

        // Assert
        assertTrue(isWin, "Diagonal win condition should be met");
    }

    @Test
    void testInvalidMoveColumnFull() {
        // Arrange
        game.makeMove(0, 'Y');
        game.makeMove(0, 'Y');
        game.makeMove(0, 'Y');
        game.makeMove(0, 'Y');
        game.makeMove(0, 'Y');
        game.makeMove(0, 'Y');

        // Act & Assert
        assertFalse(game.isValidMove(0), "Column 0 should be invalid when it's full");
    }

    @Test
    void testComputerPlayerMove() {
        // Arrange
        game.initializeBoard();
        // Simulate a move by the computer
        char playerSymbol = 'R';

        // Act
        game.makeMove(4, playerSymbol);

        // Assert
        char[][] board = game.getBoard();
        assertEquals(playerSymbol, board[5][4], "Computer's move should occupy the correct position in the column.");
    }

    @Test
    void testBoardPrinting() {
        // Arrange
        game.initializeBoard();

        // Act & Assert
        // This test ensures that printBoard does not throw an exception.
        assertDoesNotThrow(() -> game.printBoard(), "printBoard should execute without throwing an exception.");
    }
}