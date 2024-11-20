package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    @FXML
    private Button dashboard_button;

    @FXML
    private Button transactions_button;

    @FXML
    private Button accounts_button;

    @FXML
    private Button profile_button;

    @FXML
    private Button logout_button;

    @FXML
    private Button report_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboard_button.setOnAction(_ -> onDashboard());
        transactions_button.setOnAction(_ -> onTransactions());
    }

    private void onDashboard() {
        ViewFactory.getInstance().getClientSelectedMenuItem().set("Dashboard");
    }

    private void onTransactions() {
        ViewFactory.getInstance().getClientSelectedMenuItem().set("Transactions");
    }
}
