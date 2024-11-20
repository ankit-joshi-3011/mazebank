package com.jmc.mazebank.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
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
    private ListView transactions_listview;

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
        
    }
}
