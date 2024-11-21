package com.jmc.mazebank.controllers.admin;

import com.jmc.mazebank.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewFactory.getInstance().getAdminSelectedMenuItem().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case CLIENTS -> admin_parent.setCenter(ViewFactory.getInstance().getClientsView());
                case DEPOSIT -> admin_parent.setCenter(ViewFactory.getInstance().getDepositView());
                default -> admin_parent.setCenter(ViewFactory.getInstance().getCreateClientView());
            }
        }));
    }
}
