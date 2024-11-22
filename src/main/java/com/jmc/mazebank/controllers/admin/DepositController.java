package com.jmc.mazebank.controllers.admin;

import com.jmc.mazebank.models.Client;
import com.jmc.mazebank.models.Model;
import com.jmc.mazebank.views.ClientCellFactory;
import javafx.collections.ObservableList;
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
    private ListView<Client> result_listview;

    @FXML
    private TextField amount_field;

    @FXML
    Button deposit_button;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_button.setOnAction(_ -> onClientSearch());
        deposit_button.setOnAction(_ -> onDeposit());
    }

    private void onClientSearch() {
        ObservableList<Client> searchResults = Model.getInstance().searchClient(payee_address_field.getText());
        result_listview.setItems(searchResults);
        result_listview.setCellFactory(_ -> new ClientCellFactory());
        client = searchResults.getFirst();
    }

    private void onDeposit() {
        if (amount_field.getText() != null) {
            double amount = Double.parseDouble(amount_field.getText());
            double newBalance = amount + client.pensionAccountProperty().get().balanceProperty().get();

            Model.getInstance().depositSavings(payee_address_field.getText(), newBalance);
        }

        clearFields();
    }

    private void clearFields() {
        payee_address_field.clear();
        amount_field.clear();
    }
}
