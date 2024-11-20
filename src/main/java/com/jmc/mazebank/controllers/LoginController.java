package com.jmc.mazebank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
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
}
