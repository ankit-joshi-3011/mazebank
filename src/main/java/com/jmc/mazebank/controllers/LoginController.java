package com.jmc.mazebank.controllers;

import com.jmc.mazebank.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private ChoiceBox acc_selector;

    @FXML
    private Label payee_address_label;

    @FXML
    private TextField payee_address_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button login_button;

    @FXML
    private Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_button.setOnAction(_ -> ViewFactory.getInstance().showClientWindow());
    }
}
