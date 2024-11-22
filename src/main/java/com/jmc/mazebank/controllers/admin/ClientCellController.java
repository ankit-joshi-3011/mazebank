package com.jmc.mazebank.controllers.admin;

import com.jmc.mazebank.models.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    private final Client client;

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

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first_name_label.textProperty().bind(client.firstNameProperty());
        last_name_label.textProperty().bind(client.lastNameProperty());
        payee_address_label.textProperty().bind(client.payeeAddressProperty());
        savings_account_first_label.textProperty().bind(client.savingsAccountFirstProperty().asString());
        savings_account_second_label.textProperty().bind(client.savingsAccountSecondProperty().asString());
        date_label.textProperty().bind(client.dateCreatedProperty().asString());
    }
}
