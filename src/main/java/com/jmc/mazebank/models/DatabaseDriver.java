package com.jmc.mazebank.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Client Section

    // Admin Section

    // Utility methods
}
