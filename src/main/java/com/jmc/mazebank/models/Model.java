package com.jmc.mazebank.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;

    private final Client client;

    private final ObservableList<Client> clients;

    private final ObservableList<Transaction> latestTransactions;
    private final ObservableList<Transaction> allTransactions;

    private Model() {
        this.databaseDriver = new DatabaseDriver();

        client = new Client("", "", "", null, null, null);

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

    public boolean evaluateClientCredentials(String payeeAddress, String password) {
        ResultSet resultSet = databaseDriver.getClientData(payeeAddress, password);

        try {
            if (resultSet.isBeforeFirst()) {
                client.firstNameProperty().set(resultSet.getString("FirstName"));
                client.lastNameProperty().set(resultSet.getString("LastName"));
                client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));

                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
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

    private List<Transaction> prepareTransactions(int limit) {
        List<Transaction> transactions = new ArrayList<>();

        try (ResultSet resultSet = databaseDriver.getTransactions(client.payeeAddressProperty().get(), limit)) {
            while (resultSet.next()) {
                String sender = resultSet.getString("Sender");
                String receiver = resultSet.getString("Receiver");
                double amount = resultSet.getDouble("Amount");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                String message = resultSet.getString("Message");

                transactions.add(new Transaction(sender, receiver, amount, date, message));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public ObservableList<Transaction> getLatestTransactions() {
        if (latestTransactions.isEmpty()) {
            latestTransactions.addAll(prepareTransactions(4));
        }

        return latestTransactions;
    }

    public ObservableList<Transaction> getAllTransactions() {
        if (allTransactions.isEmpty()) {
            allTransactions.addAll(prepareTransactions(-1));
        }

        return allTransactions;
    }

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

    public void createSavingsAccount(String owner, String accountNumber, Double transactionLimit, Double balance) {
        databaseDriver.createSavingsAccount(owner, accountNumber, transactionLimit, balance);
    }

    public void createPensionAccount(String owner, String accountNumber, Double withdrawalLimit, Double balance) {
        databaseDriver.createPensionAccount(owner, accountNumber, withdrawalLimit, balance);
    }

    public void createClient(String firstName, String lastName, String payeeAddress, String password, LocalDate date) {
        databaseDriver.createClient(firstName, lastName, payeeAddress, password, date);
    }

    public int getLastClientId() {
        int id = 0;

        try (ResultSet resultSet = databaseDriver.getLastClientId()) {
            id = resultSet.getInt("seq");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients() {
        SavingsAccount savingsAccount;
        PensionAccount pensionAccount;
        ResultSet resultSet = databaseDriver.getAllClientsData();

        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String payeeAddress = resultSet.getString("PayeeAddress");

                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

                savingsAccount = getSavingsAccount(payeeAddress);
                pensionAccount = getPensionAccount(payeeAddress);

                clients.add(new Client(firstName, lastName, payeeAddress, savingsAccount, pensionAccount, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newTransaction(String sender, String receiver, double amount, String message) {
        LocalDate date = LocalDate.now();

        databaseDriver.newTransaction(sender, receiver, amount, date, message);

        Transaction newTransaction = new Transaction(sender, receiver, amount, date, message);

        latestTransactions.addFirst(newTransaction);
        allTransactions.addFirst(newTransaction);
    }

    public SavingsAccount getSavingsAccount(String payeeAddress) {
        SavingsAccount savingsAccount = null;
        ResultSet resultSet = databaseDriver.getAccountData(payeeAddress, true);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            int transactionLimit = (int) resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            String[] dateParts = resultSet.getString("DateCreated").split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

            savingsAccount = new SavingsAccount(payeeAddress, accountNumber, balance, date, transactionLimit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return savingsAccount;
    }

    public PensionAccount getPensionAccount(String payeeAddress) {
        PensionAccount pensionAccount = null;
        ResultSet resultSet = databaseDriver.getAccountData(payeeAddress, false);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            int withdrawalLimit = (int) resultSet.getDouble("WithdrawalLimit");
            double balance = resultSet.getDouble("Balance");
            String[] dateParts = resultSet.getString("DateCreated").split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

            pensionAccount = new PensionAccount(payeeAddress, accountNumber, balance, date, withdrawalLimit);
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
            LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

            searchResults.add(new Client(firstName, lastName, payeeAddress, getSavingsAccount(payeeAddress), getPensionAccount(payeeAddress), date));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    public void updateSavingsAccountBalance(String payeeAddress, double balance) {
        databaseDriver.updateAccountBalance(payeeAddress, balance, true);
    }

    public void updatePensionAccountBalance(String payeeAddress, double balance) {
        databaseDriver.updateAccountBalance(payeeAddress, balance, false);
    }
}
