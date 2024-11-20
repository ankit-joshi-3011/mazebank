package com.jmc.mazebank.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    @FXML
    private Label savings_account_first_number;

    @FXML
    private Label savings_account_first_transaction_limit;

    @FXML
    private Label savings_account_first_date_created;

    @FXML
    private Label savings_account_first_balance;

    @FXML
    private Label savings_account_second_number;

    @FXML
    private Label savings_account_second_transaction_limit;

    @FXML
    private Label savings_account_second_date_created;

    @FXML
    private Label savings_account_second_balance;

    @FXML
    private TextField amount_to_savings_account_second;

    @FXML
    private Button transfer_to_savings_account_second;

    @FXML
    private TextField amount_to_savings_account_first;

    @FXML
    private Button transfer_to_savings_account_first;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
