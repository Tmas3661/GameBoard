import org.junit.jupiter.api.*;
import org.progtech.db.DBManager;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;

class DBManagerTest {

    private DBManager dbManager;

    @BeforeEach
    void setUp() {
        dbManager = new DBManager();
    }

    @Test
    void testSaveWin() {
        // Arrange
        String testPlayerName = "TestPlayer";

        // Act
        dbManager.saveWin(testPlayerName);

        // Assert
        // Re-fetch player data to check if winNum increased
        int initialWins = dbManager.getPlayerWins(testPlayerName); // Add a helper method to fetch player's win count
        dbManager.saveWin(testPlayerName);
        int updatedWins = dbManager.getPlayerWins(testPlayerName);

        assertEquals(initialWins + 1, updatedWins, "Win count should increment by 1");
    }

    @Test
    void testGetTopWinners() {
        // Act & Assert
        assertDoesNotThrow(() -> dbManager.getTopWinners(), "Fetching top winners should not throw exceptions");
    }
}