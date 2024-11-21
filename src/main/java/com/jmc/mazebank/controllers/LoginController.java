package com.jmc.mazebank.controllers;

import com.jmc.mazebank.views.AccountType;
import com.jmc.mazebank.views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private ChoiceBox<AccountType> acc_selector;

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
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(ViewFactory.getInstance().getLoginAccountType());
        acc_selector.valueProperty().addListener(_ -> ViewFactory.getInstance().setLoginAccountType(acc_selector.getValue()));
        login_button.setOnAction(_ -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) error_label.getScene().getWindow();
        ViewFactory.getInstance().closeStage(stage);

        if (ViewFactory.getInstance().getLoginAccountType() == AccountType.CLIENT) {
            ViewFactory.getInstance().showClientWindow();
        } else {
            ViewFactory.getInstance().showAdminWindow();
        }
    }
}
