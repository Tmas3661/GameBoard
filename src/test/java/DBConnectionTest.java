import org.junit.jupiter.api.*;
import org.progtech.db.DBConnection;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;

class DBConnectionTest {

    private DBConnection dbConnection;

    @BeforeEach
    void setUp() {
        dbConnection = new DBConnection();
    }

    @Test
    void testGetConnection() {
        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNotNull(connection, "Database connection should not be null");
    }
}
