package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.views.ClientMenuOptions;
import com.jmc.mazebank.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    private Button logout_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboard_button.setOnAction(_ -> onDashboard());
        transactions_button.setOnAction(_ -> onTransactions());
        accounts_button.setOnAction(_ -> onAccounts());
        logout_button.setOnAction(_ -> onLogout());
    }

    private void onDashboard() {
        ViewFactory.getInstance().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onTransactions() {
        ViewFactory.getInstance().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts() {
        ViewFactory.getInstance().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
    }

    private void onLogout() {
        Stage stage = (Stage) dashboard_button.getScene().getWindow();

        ViewFactory.getInstance().closeStage(stage);

        ViewFactory.getInstance().showLoginWindow();
    }
}
