package org.progtech.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DBManager {
    private DBConnection dbConnection;

    public DBManager() {
        dbConnection = new DBConnection();
    }

    public void getTopWinners() {
        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT name, winNum FROM results ORDER BY winNum DESC LIMIT 3";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            int rank = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                int wins = rs.getInt("winNum");
                System.out.println(rank + ". " + name + " - " + wins + " wins");
                rank++;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving top winners: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveWin(String playerName) {
        try (Connection connection = dbConnection.getConnection()) {
            String checkQuery = "SELECT * FROM results WHERE name = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, playerName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String updateQuery = "UPDATE results SET winNum = winNum + 1 WHERE name = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setString(1, playerName);
                updateStmt.executeUpdate();
            } else {
                String insertQuery = "INSERT INTO results (name, winNum) VALUES (?, 1)";
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, playerName);
                insertStmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error saving win: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getPlayerWins(String playerName) {
        int wins = 0;

        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT winNum FROM results WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                wins = rs.getInt("winNum");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving player wins: " + e.getMessage());
            e.printStackTrace();
        }

        return wins;
    }
}