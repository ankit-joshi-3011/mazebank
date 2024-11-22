package com.jmc.mazebank.models;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getClientData(String payeeAddress, String password) {
        return tryExecuteQuery("SELECT * FROM Clients WHERE PayeeAddress = '" + payeeAddress + "' AND Password = '" + password + "';");
    }

    public ResultSet getTransactions(String payeeAddress, int limit) {
        return tryExecuteQuery("SELECT * FROM Transactions WHERE Sender = '" + payeeAddress + "' OR Receiver = '" + payeeAddress + "' ORDER BY Date DESC LIMIT " + limit + ";");
    }

    public void newTransaction(String sender, String receiver, double amount, LocalDate date, String message) {
        tryExecuteUpdate("INSERT INTO " + "Transactions(Sender, Receiver, Amount, Date, Message) " + "VALUES('" + sender + "', '" + receiver + "', " + amount + ", '" + date + "', '" + message + "');");
    }

    public ResultSet getAdminData(String userName, String password) {
        return tryExecuteQuery("SELECT * FROM Admins WHERE Username = '" + userName + "' AND Password = '" + password + "';");
    }

    public void createClient(String firstName, String lastName, String payeeAddress, String password, LocalDate date) {
        tryExecuteUpdate("INSERT INTO " + "Clients(FirstName, LastName, PayeeAddress, Password, Date)" + "VALUES ('" + firstName + "', '" + lastName + "', '" + payeeAddress + "', '" + password + "', '" + date.toString() + "');");
    }

    public void createSavingsAccount(String owner, String accountNumber, Double transactionLimit, Double balance) {
        tryExecuteUpdate("INSERT INTO " + "SavingsAccount(Owner, AccountNumber, TransactionLimit, Balance, DateCreated)" + "VALUES ('" + owner + "', '" + accountNumber + "', " + transactionLimit + ", " + balance + ", '" + LocalDate.now() + "');");
    }

    public void createPensionAccount(String owner, String accountNumber, Double withdrawalLimit, Double balance) {
        tryExecuteUpdate("INSERT INTO " + "PensionAccount(Owner, AccountNumber, WithdrawalLimit, Balance, DateCreated)" + "VALUES ('" + owner + "', '" + accountNumber + "', " + withdrawalLimit + ", " + balance + ", '" + LocalDate.now() + "');");
    }

    public ResultSet getAllClientsData() {
        return tryExecuteQuery("SELECT * FROM Clients;");
    }

    public ResultSet getLastClientId() {
        return tryExecuteQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
    }

    public ResultSet getAccountData(String payeeAddress, boolean savingsOrPension) {
        return tryExecuteQuery("SELECT * FROM " + (savingsOrPension ? "SavingsAccount" : "PensionAccount") + " WHERE owner = '" + payeeAddress + "';");
    }

    public ResultSet searchClient(String payeeAddress) {
        return tryExecuteQuery("SELECT * FROM Clients WHERE PayeeAddress = '" + payeeAddress + "';");
    }

    public void updateAccountBalance(String payeeAddress, double balance, boolean savingsOrPension) {
        tryExecuteUpdate("UPDATE " + (savingsOrPension ? "SavingsAccount" : "PensionAccount") + " SET Balance = " + balance + " WHERE Owner = '" + payeeAddress + "';");
    }

    private Statement tryCreateStatement() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statement;
    }

    private void tryExecuteUpdate(String updateQuery) {
        Optional<Statement> statement = Optional.ofNullable(tryCreateStatement());

        if (statement.isPresent()) {
            try {
                statement.get().executeUpdate(updateQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ResultSet tryExecuteQuery(String query) {
        Optional<Statement> statement = Optional.ofNullable(tryCreateStatement());
        ResultSet resultSet = null;

        if (statement.isPresent()) {
            try {
                resultSet = statement.get().executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultSet;
    }
}
