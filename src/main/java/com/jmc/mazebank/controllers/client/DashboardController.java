package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.models.Client;
import com.jmc.mazebank.models.Model;
import com.jmc.mazebank.models.Transaction;
import com.jmc.mazebank.views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Text user_name;

    @FXML
    private Label login_date;

    @FXML
    private Label savings_account_balance;

    @FXML
    private Label savings_account_number;

    @FXML
    private Label pension_account_balance;

    @FXML
    private Label pension_account_number;

    @FXML
    private Label income_label;

    @FXML
    private Label expense_label;

    @FXML
    private ListView<Transaction> transactions_listview;

    @FXML
    private TextField payee_address_field;

    @FXML
    private TextField amount_field;

    @FXML
    private TextArea message_field;

    @FXML
    private Button send_money_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        transactions_listview.setItems(Model.getInstance().getLatestTransactions());
        transactions_listview.setCellFactory(_ -> new TransactionCellFactory());
        send_money_button.setOnAction(_ -> onSendMoney());
        calculateAccountSummary();
        Model.getInstance().getAllTransactions().addListener((ListChangeListener<Transaction>) _ -> calculateAccountSummary());
    }

    private void bindData() {
        user_name.textProperty().bind(Bindings.concat("Hi, ", Model.getInstance().getClient().firstNameProperty()));
        login_date.setText("Today, " + LocalDate.now());
        savings_account_balance.textProperty().bind(Bindings.concat("Rs. ", Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty()));
        savings_account_number.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
        pension_account_balance.textProperty().bind(Bindings.concat("Rs. ", Model.getInstance().getClient().pensionAccountProperty().get().balanceProperty()));
        pension_account_number.textProperty().bind(Model.getInstance().getClient().pensionAccountProperty().get().accountNumberProperty());
    }

    private void onSendMoney() {
        String payeeAddress = payee_address_field.getText();
        double amount = Double.parseDouble(amount_field.getText());
        String message = message_field.getText();
        String sender = Model.getInstance().getClient().payeeAddressProperty().get();
        ObservableList<Client> searchResults = Model.getInstance().searchClient(payeeAddress);

        if (!searchResults.isEmpty()) {
            double senderSavingsAccountBalance = Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().get();

            if (amount <= senderSavingsAccountBalance) {
                double senderNewSavingsAccountBalance = senderSavingsAccountBalance - amount;
                Model.getInstance().updateSavingsAccountBalance(sender, senderNewSavingsAccountBalance);
                Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().set(senderNewSavingsAccountBalance);
            }

            double receiverNewSavingsAccountBalance = searchResults.getFirst().savingsAccountProperty().get().balanceProperty().get() + amount;
            Model.getInstance().updateSavingsAccountBalance(searchResults.getFirst().savingsAccountProperty().get().ownerProperty().get(), receiverNewSavingsAccountBalance);
            searchResults.getFirst().savingsAccountProperty().get().balanceProperty().set(receiverNewSavingsAccountBalance);

            Model.getInstance().newTransaction(sender, searchResults.getFirst().savingsAccountProperty().get().ownerProperty().get(), amount, message);

            clearFields();
        }
    }

    private void clearFields() {
        payee_address_field.clear();
        amount_field.clear();
        message_field.clear();
    }

    private void calculateAccountSummary() {
        double income = 0.0;
        double expense = 0.0;

        for (Transaction transaction : Model.getInstance().getAllTransactions()) {
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().payeeAddressProperty().get())) {
                expense = expense + transaction.amountProperty().get();
            } else {
                income = income + transaction.amountProperty().get();
            }
        }

        income_label.setText("+ Rs. " + income);
        expense_label.setText("- Rs. " + expense);
    }
}
