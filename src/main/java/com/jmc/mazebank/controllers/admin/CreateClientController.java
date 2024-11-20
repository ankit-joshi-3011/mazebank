package com.jmc.mazebank.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    @FXML
    TextField first_name_field;

    @FXML
    TextField last_name_field;

    @FXML
    TextField password_field;

    @FXML
    CheckBox payee_address_box;

    @FXML
    Label payee_address_label;

    @FXML
    CheckBox savings_account_first_box;

    @FXML
    TextField savings_account_first_amount_field;

    @FXML
    CheckBox savings_account_second_box;

    @FXML
    TextField savings_account_second_amount_field;

    @FXML
    Button create_client_button;

    @FXML
    Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
