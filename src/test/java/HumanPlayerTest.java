import org.junit.jupiter.api.*;
import org.progtech.player.HumanPlayer;

import static org.junit.jupiter.api.Assertions.*;

class HumanPlayerTest {

    private HumanPlayer player;

    @BeforeEach
    void setUp() {
        player = new HumanPlayer('Y');
    }

    @Test
    void testGetSymbol() {
        assertEquals('Y', player.getSymbol(), "Player's symbol should match the one assigned");
    }
}
