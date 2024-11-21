package com.jmc.mazebank.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;

    // Client Data Section
    private final Client client;

    private ObservableList<Client> clients;

    // Admin Data Section

    private Model() {
        this.databaseDriver = new DatabaseDriver();

        // Client Data Section
        client = new Client("", "", "", null, null, null);

        // Admin Data Section
        clients = FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    // Client method section
    public boolean evaluateClientCredentials(String payeeAddress, String password) {
        ResultSet resultSet = databaseDriver.getClientData(payeeAddress, password);

        try {
            if (resultSet.isBeforeFirst()) {
                client.firstNameProperty().set(resultSet.getString("FirstName"));
                client.lastNameProperty().set(resultSet.getString("LastName"));
                client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));

                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(
                    Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]),
                    Integer.parseInt(dateParts[2])
                );
                client.dateCreatedProperty().set(date);
                client.savingsAccountFirstProperty().set(getSavingsAccountFirst(payeeAddress));
                client.savingsAccountSecondProperty().set(getSavingsAccountSecond(payeeAddress));

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Admin method section
    public boolean evaluateAdminCredentials(String userName, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(userName, password);

        try {
            if (resultSet.isBeforeFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void createSavingsAccountFirst(String owner, String accountNumber, Double transactionLimit, Double balance) {
        databaseDriver.createSavingsAccountFirst(owner, accountNumber, transactionLimit, balance);
    }

    public void createSavingsAccountSecond(String owner, String accountNumber, Double transactionLimit, Double balance) {
        databaseDriver.createSavingsAccountSecond(owner, accountNumber, transactionLimit, balance);
    }

    public void createClient(String firstName, String lastName, String payeeAddress, String password, LocalDate date) {
        databaseDriver.createClient(firstName, lastName, payeeAddress, password, date);
    }

    public int getLastClientId() {
        return databaseDriver.getLastClientId();
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients() {
        SavingsAccount savingsAccountFirst;
        SavingsAccount savingsAccountSecond;
        ResultSet resultSet = databaseDriver.getAllClientsData();

        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String payeeAddress = resultSet.getString("PayeeAddress");

                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(
                    Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]),
                    Integer.parseInt(dateParts[2])
                );

                savingsAccountFirst = getSavingsAccountFirst(payeeAddress);
                savingsAccountSecond = getSavingsAccountSecond(payeeAddress);

                clients.add(new Client(firstName, lastName, payeeAddress, savingsAccountFirst, savingsAccountSecond, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Utility Methods
    public SavingsAccount getSavingsAccountFirst(String payeeAddress) {
        SavingsAccount savingsAccountFirst = null;
        ResultSet resultSet = databaseDriver.getSavingsAccountFirstData(payeeAddress);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            int transactionLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            savingsAccountFirst = new SavingsAccount(payeeAddress, accountNumber, balance, transactionLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return savingsAccountFirst;
    }

    public SavingsAccount getSavingsAccountSecond(String payeeAddress) {
        SavingsAccount savingsAccountSecond = null;
        ResultSet resultSet = databaseDriver.getSavingsAccountSecondData(payeeAddress);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            int transactionLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            savingsAccountSecond = new SavingsAccount(payeeAddress, accountNumber, balance, transactionLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return savingsAccountSecond;
    }

    public ObservableList<Client> searchClient(String payeeAddress) {
        ObservableList<Client> searchResults = FXCollections.observableArrayList();

        try (ResultSet resultSet = databaseDriver.searchClient(payeeAddress)) {
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String[] dateParts = resultSet.getString("Date").split("-");
            LocalDate date = LocalDate.of(
                Integer.parseInt(dateParts[0]),
                Integer.parseInt(dateParts[1]),
                Integer.parseInt(dateParts[2])
            );

            searchResults.add(new Client(firstName, lastName, payeeAddress, getSavingsAccountFirst(payeeAddress), getSavingsAccountSecond(payeeAddress), date));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    public void depositSavings(String payeeAddress, double amount) {
        databaseDriver.depositSavings(payeeAddress, amount);
    }
}
