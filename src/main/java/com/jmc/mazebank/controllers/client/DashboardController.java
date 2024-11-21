package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.models.Model;
import com.jmc.mazebank.models.Transaction;
import com.jmc.mazebank.views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
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
    private Label savings_account_first_balance;

    @FXML
    private Label savings_account_first_number;

    @FXML
    private Label savings_account_second_balance;

    @FXML
    private Label savings_account_second_number;

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
        initializeLatestTransactionsList();
        transactions_listview.setItems(Model.getInstance().getLatestTransactions());
        transactions_listview.setCellFactory(_ -> new TransactionCellFactory());
    }

    private void bindData() {
        user_name.textProperty().bind(Bindings.concat("Hi, ", Model.getInstance().getClient().firstNameProperty()));
        login_date.setText("Today, " + LocalDate.now());
        savings_account_first_balance.textProperty().bind(Model.getInstance().getClient().savingsAccountFirstProperty().get().balanceProperty().asString());
        savings_account_first_number.textProperty().bind(Model.getInstance().getClient().savingsAccountFirstProperty().get().accountNumberProperty());
        savings_account_second_balance.textProperty().bind(Model.getInstance().getClient().savingsAccountSecondProperty().get().balanceProperty().asString());
        savings_account_second_number.textProperty().bind(Model.getInstance().getClient().savingsAccountSecondProperty().get().accountNumberProperty());
    }

    private void initializeLatestTransactionsList() {
        Model.getInstance().setLatestTransactions();
    }
}
