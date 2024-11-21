package com.jmc.mazebank.controllers.admin;

import com.jmc.mazebank.models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
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

    private String payeeAddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_button.setOnAction(_ -> createClient());
        payee_address_box.selectedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        });
    }

    private void createClient() {
        if (savings_account_first_box.isSelected()) {
            createSavingsAccountFirst();
        }

        if (savings_account_second_box.isSelected()) {
            createSavingsAccountSecond();
        }

        String firstName = first_name_field.getText();
        String lastName = last_name_field.getText();
        String password = password_field.getText();

        Model.getInstance().createClient(firstName, lastName, payeeAddress, password, LocalDate.now());

        error_label.setStyle("-fx-text-fill: green; -fx-font-size: 1.3em; -fx-font-weight: bold");
        error_label.setText("Client created successfully");

        clearFields();
    }

    private void createSavingsAccountFirst() {
        double balance = Double.parseDouble(savings_account_first_amount_field.getText());

        // Generate Account Number
        String firstSection = "3201";
        String lastSection = Integer.toString(new Random().nextInt(9999) + 1000);
        String accountNumber = firstSection + " " + lastSection;

        // Create the account
        Model.getInstance().createSavingsAccountFirst(payeeAddress, accountNumber, (double) 10, 50000.0);
    }

    private void createSavingsAccountSecond() {
        double balance = Double.parseDouble(savings_account_second_amount_field.getText());

        // Generate Account Number
        String firstSection = "3201";
        String lastSection = Integer.toString(new Random().nextInt(9999) + 1000);
        String accountNumber = firstSection + " " + lastSection;

        // Create the account
        Model.getInstance().createSavingsAccountSecond(payeeAddress, accountNumber, (double) 10, balance);
    }

    private void onCreatePayeeAddress() {
        if (first_name_field.getText() != null && last_name_field.getText() != null) {
            payee_address_label.setText(payeeAddress);
        }
    }

    private String createPayeeAddress() {
        int id = Model.getInstance().getLastClientId() + 1;

        char firstChar = Character.toLowerCase(first_name_field.getText().charAt(0));

        return "@" + firstChar + last_name_field.getText() + id;
    }

    private void clearFields() {
        first_name_field.clear();
        last_name_field.clear();
        password_field.clear();
        payee_address_box.setSelected(false);
        payee_address_label.setText("");
        savings_account_first_box.setSelected(false);
        savings_account_first_amount_field.clear();
        savings_account_second_box.setSelected(false);
        savings_account_second_amount_field.clear();
    }
}
