package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.models.Model;
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
        savings_account_number.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
        savings_account_transaction_limit.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().transactionLimitProperty().asString());
        // TODO: Date Created Binding
        savings_account_balance.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
    }
}
