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

    private final ObservableList<Client> clients;
    private final ObservableList<Transaction> latestTransactions;
    private final ObservableList<Transaction> allTransactions;

    // Admin Data Section

    private Model() {
        this.databaseDriver = new DatabaseDriver();

        // Client Data Section
        client = new Client("", "", "", null, null, null);

        // Admin Data Section
        clients = FXCollections.observableArrayList();
        latestTransactions = FXCollections.observableArrayList();
        allTransactions = FXCollections.observableArrayList();
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
                client.savingsAccountProperty().set(getSavingsAccount(payeeAddress));
                client.pensionAccountProperty().set(getPensionAccount(payeeAddress));

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Client getClient() {
        return client;
    }

    private void prepareTransactions(ObservableList<Transaction> transactions, int limit) {
        ResultSet resultSet = databaseDriver.getTransactions(client.payeeAddressProperty().get(), limit);

        try {
            while (resultSet.next()) {
                String sender = resultSet.getString("Sender");
                String receiver = resultSet.getString("Receiver");
                double amount = resultSet.getDouble("Amount");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(
                        Integer.parseInt(dateParts[0]),
                        Integer.parseInt(dateParts[1]),
                        Integer.parseInt(dateParts[2])
                );
                String message = resultSet.getString("Message");

                transactions.add(new Transaction(sender, receiver, amount, date, message));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLatestTransactions() {
        prepareTransactions(latestTransactions, 4);
    }

    public ObservableList<Transaction> getLatestTransactions() {
        return latestTransactions;
    }

    public void setAllTransactions() {
        prepareTransactions(allTransactions, -1);
    }

    public ObservableList<Transaction> getAllTransactions() {
        return allTransactions;
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

                savingsAccountFirst = getSavingsAccount(payeeAddress);
                savingsAccountSecond = getPensionAccount(payeeAddress);

                clients.add(new Client(firstName, lastName, payeeAddress, savingsAccountFirst, savingsAccountSecond, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBalance(String receiver, double amount, boolean addOrSubtract) {
        databaseDriver.updateBalance(receiver, amount, addOrSubtract);
        client.pensionAccountProperty().get().setBalance(amount, false);
    }

    public void newTransaction(String sender, String receiver, double amount, String message) {
        databaseDriver.newTransaction(sender, receiver, amount, message);
    }

    // Utility Methods
    public SavingsAccount getSavingsAccount(String payeeAddress) {
        SavingsAccount savingsAccount = null;
        ResultSet resultSet = databaseDriver.getSavingsAccountData(payeeAddress);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            int transactionLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            String[] dateParts = resultSet.getString("DateCreated").split("-");
            LocalDate date = LocalDate.of(
                    Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]),
                    Integer.parseInt(dateParts[2])
            );

            savingsAccount = new SavingsAccount(payeeAddress, accountNumber, balance, date, transactionLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return savingsAccount;
    }

    public SavingsAccount getPensionAccount(String payeeAddress) {
        SavingsAccount pensionAccount = null;
        ResultSet resultSet = databaseDriver.getPensionAccountData(payeeAddress);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            int transactionLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            String[] dateParts = resultSet.getString("DateCreated").split("-");
            LocalDate date = LocalDate.of(
                    Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]),
                    Integer.parseInt(dateParts[2])
            );

            pensionAccount = new SavingsAccount(payeeAddress, accountNumber, balance, date, transactionLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pensionAccount;
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

            searchResults.add(new Client(firstName, lastName, payeeAddress, getSavingsAccount(payeeAddress), getPensionAccount(payeeAddress), date));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    public void depositSavings(String payeeAddress, double amount) {
        databaseDriver.depositSavings(payeeAddress, amount);
    }
}
