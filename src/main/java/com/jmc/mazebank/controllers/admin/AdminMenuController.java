package com.jmc.mazebank.controllers.admin;

import com.jmc.mazebank.views.AdminMenuOptions;
import com.jmc.mazebank.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    @FXML
    private Button create_client_button;

    @FXML
    private Button clients_button;

    @FXML
    private Button deposit_button;

    @FXML
    private Button logout_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        create_client_button.setOnAction(_ -> onCreateClient());
    }

    private void onCreateClient() {
        ViewFactory.getInstance().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }
}
