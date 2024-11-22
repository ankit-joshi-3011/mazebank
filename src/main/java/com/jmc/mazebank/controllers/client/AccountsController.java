package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.models.Model;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
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
        savings_account_date_created.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().dateCreatedProperty().asString());
        savings_account_balance.textProperty().bind(Bindings.format("%s", NumberFormat.getCurrencyInstance(Locale.getDefault()).format(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().get())));

        pension_account_number.textProperty().bind(Model.getInstance().getClient().pensionAccountProperty().get().accountNumberProperty());
        pension_account_withdrawal_limit.textProperty().bind(Bindings.format("%s", NumberFormat.getCurrencyInstance(Locale.getDefault()).format(Model.getInstance().getClient().pensionAccountProperty().get().withdrawalLimitProperty().get())));
        pension_account_date_created.textProperty().bind(Model.getInstance().getClient().pensionAccountProperty().get().dateCreatedProperty().asString());
        pension_account_balance.textProperty().bind(Bindings.format("%s", NumberFormat.getCurrencyInstance(Locale.getDefault()).format(Model.getInstance().getClient().pensionAccountProperty().get().balanceProperty().get())));

        transfer_to_pension_account.setOnAction(_ -> onTransferToPensionAccount());
    }

    private void onTransferToPensionAccount() {
        double amount = Double.parseDouble(amount_to_pension_account.getText());
        double savingsAccountBalance = Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().get();

        if (amount <= savingsAccountBalance) {
            double newPensionAccountBalance = amount + Model.getInstance().getClient().pensionAccountProperty().get().balanceProperty().get();
            Model.getInstance().updatePensionAccountBalance(Model.getInstance().getClient().pensionAccountProperty().get().ownerProperty().get(), newPensionAccountBalance);
            Model.getInstance().getClient().pensionAccountProperty().get().balanceProperty().set(newPensionAccountBalance);

            double newSavingsAccountBalance = Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().get() - amount;
            Model.getInstance().updateSavingsAccountBalance(Model.getInstance().getClient().savingsAccountProperty().get().ownerProperty().get(), newSavingsAccountBalance);
            Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().set(newSavingsAccountBalance);

            Model.getInstance().newTransaction(Model.getInstance().getClient().savingsAccountProperty().get().ownerProperty().get(), Model.getInstance().getClient().pensionAccountProperty().get().ownerProperty().get(), amount, "Transfer to Pension Account");

            amount_to_pension_account.clear();
        }
    }
}
