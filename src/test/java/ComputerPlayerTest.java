import org.junit.jupiter.api.*;
import org.progtech.player.ComputerPlayer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {

    private ComputerPlayer player;

    @BeforeEach
    void setUp() {
        player = new ComputerPlayer('R');
    }

    @Test
    void testGetMove() {
        // Arrange
        char[][] board = new char[6][7];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        // Act
        int move = player.getMove(board);

        // Assert
        assertTrue(move >= 0 && move < 7, "Move should be within column range");
    }
}