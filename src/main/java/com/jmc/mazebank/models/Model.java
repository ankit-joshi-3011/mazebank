package com.jmc.mazebank.models;

import com.jmc.mazebank.views.AccountType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final DatabaseDriver databaseDriver;

    private AccountType loginAccountType;

    // Client Data Section
    private Client client;

    // Admin Data Section

    private Model() {
        this.databaseDriver = new DatabaseDriver();

        this.loginAccountType = AccountType.CLIENT;

        // Client Data Section
        client = new Client("", "", "", null, null, null);

        // Admin Data Section
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
                LocalDate date = LocalDate.of(
                    Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]),
                    Integer.parseInt(dateParts[2])
                );
                client.dateCreatedProperty().set(date);

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
