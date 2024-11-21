package com.jmc.mazebank.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    @FXML
    private TextField payee_address_field;

    @FXML
    private Button search_button;

    @FXML
    private ListView result_listview;

    @FXML
    private TextField amount_field;

    @FXML
    Button deposit_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
