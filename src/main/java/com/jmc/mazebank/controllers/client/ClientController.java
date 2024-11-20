package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private BorderPane client_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewFactory.getInstance().getClientSelectedMenuItem().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case TRANSACTIONS -> client_parent.setCenter(ViewFactory.getInstance().getTransactionsView());
                case ACCOUNTS -> client_parent.setCenter(ViewFactory.getInstance().getAccountsView());
                default -> client_parent.setCenter(ViewFactory.getInstance().getDashboardView());
            }
        }));
    }
}
