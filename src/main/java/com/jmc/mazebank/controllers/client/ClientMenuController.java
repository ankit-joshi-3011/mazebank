package com.jmc.mazebank.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
    private Button profile_button;

    @FXML
    private Button logout_button;

    @FXML
    private Button report_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
