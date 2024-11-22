package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.models.Model;
import com.jmc.mazebank.models.Transaction;
import com.jmc.mazebank.views.TransactionCellFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    @FXML
    private ListView<Transaction> transactions_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transactions_listview.setItems(Model.getInstance().getAllTransactions());
        transactions_listview.setCellFactory(_ -> new TransactionCellFactory());
    }
}
