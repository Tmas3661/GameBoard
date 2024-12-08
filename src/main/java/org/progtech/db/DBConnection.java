package org.progtech.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "boarddb";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }

        return databaseLink;
    }
}
