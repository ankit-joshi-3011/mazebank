package com.jmc.mazebank.controllers.admin;

import com.jmc.mazebank.models.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    @FXML
    private Label first_name_label;

    @FXML
    private Label last_name_label;

    @FXML
    private Label payee_address_label;

    @FXML
    private Label savings_account_first_label;

    @FXML
    private Label savings_account_second_label;

    @FXML
    private Label date_label;

    @FXML
    private Button delete_button;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
