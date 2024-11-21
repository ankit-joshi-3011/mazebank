package com.jmc.mazebank.models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Client Section
    public ResultSet getClientData(String payeeAddress, String password) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress = '" + payeeAddress + "' AND Password = '" + password + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Admin Section
    public ResultSet getAdminData(String userName, String password) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username = '" + userName + "' AND Password = '" + password + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void createClient(String firstName, String lastName, String payeeAddress, String password, LocalDate date) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                "Clients(FirstName, LastName, PayeeAddress, Password, Date)" +
                "VALUES ('" + firstName + "', '" + lastName + "', '" + payeeAddress + "', '" + password + "', '" + date.toString() + "');"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSavingsAccountFirst(String owner, String accountNumber, Double transactionLimit, Double balance) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "SavingsAccountFirst(Owner, AccountNumber, TransactionLimit, Balance)" +
                    "VALUES ('" + owner + "', '" + accountNumber + "', " + transactionLimit + ", " + balance + ");"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSavingsAccountSecond(String owner, String accountNumber, Double transactionLimit, Double balance) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "SavingsAccountSecond(Owner, AccountNumber, TransactionLimit, Balance)" +
                    "VALUES ('" + owner + "', '" + accountNumber + "', " + transactionLimit + ", " + balance + ");"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllClientsData() {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Utility methods
    public int getLastClientId() {
        Statement statement = null;
        ResultSet resultSet = null;

        int id = 0;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public ResultSet getSavingsAccountFirstData(String payeeAddress) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccountFirst WHERE owner = '" + payeeAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getSavingsAccountSecondData(String payeeAddress) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccountSecond WHERE owner = '" + payeeAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
