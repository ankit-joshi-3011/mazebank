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
    private Label savings_account_number;

    @FXML
    private Label savings_account_transaction_limit;

    @FXML
    private Label savings_account_date_created;

    @FXML
    private Label savings_account_balance;

    @FXML
    private Label pension_account_number;

    @FXML
    private Label pension_account_withdrawal_limit;

    @FXML
    private Label pension_account_date_created;

    @FXML
    private Label pension_account_balance;

    @FXML
    private TextField amount_to_pension_account;

    @FXML
    private Button transfer_to_pension_account;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
