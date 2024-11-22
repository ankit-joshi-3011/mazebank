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

    public ResultSet getTransactions(String payeeAddress, int limit) {
        return tryExecuteQuery("SELECT * FROM Transactions WHERE Sender = '" + payeeAddress + "' OR Receiver = '" + payeeAddress + "' ORDER BY Date DESC LIMIT " + limit + ";");
    }

    public void updateBalance(String payeeAddress, double amount, boolean addOrSubtract) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccountSecond WHERE Owner = '" + payeeAddress + "';");

            double newBalance;
            if (addOrSubtract) {
                newBalance = resultSet.getDouble("Balance") + amount;
                statement.executeUpdate("UPDATE SavingsAccountSecond SET Balance = " + newBalance + " WHERE Owner = '" + payeeAddress + "';");
            } else {
                if (resultSet.getDouble("Balance") > amount) {
                    newBalance = resultSet.getDouble("Balance") - amount;
                    statement.executeUpdate("UPDATE SavingsAccountSecond SET Balance = " + newBalance + " WHERE Owner = '" + payeeAddress + "';");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newTransaction(String sender, String receiver, double amount, String message) {
        tryExecuteUpdate("INSERT INTO " +
            "Transactions(Sender, Receiver, Amount, Date, Message) " +
            "VALUES('" + sender + "', '" + receiver + "', " + amount + ", '" + LocalDate.now() + "', '" + message + "');"
        );
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

    public ResultSet getSavingsAccountData(String payeeAddress) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccount WHERE owner = '" + payeeAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getPensionAccountData(String payeeAddress) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM PensionAccount WHERE owner = '" + payeeAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet searchClient(String payeeAddress) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress = '" + payeeAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void depositSavings(String payeeAddress, double amount) {
        Statement statement;

        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE SavingsAccountSecond SET Balance = " + amount + " WHERE Owner = '" + payeeAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSavingsAccountBalance(String payeeAddress, double balance) {
        tryExecuteUpdate("UPDATE SavingsAccount SET Balance = " + balance + " WHERE Owner = '" + payeeAddress + "';");
    }

    public void updatePensionAccountBalance(String payeeAddress, double balance) {
        tryExecuteUpdate("UPDATE PensionAccount SET Balance = " + balance + " WHERE Owner = '" + payeeAddress + "';");
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
