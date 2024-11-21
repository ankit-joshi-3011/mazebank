package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    @FXML
    private FontAwesomeIconView in_icon;

    @FXML
    private FontAwesomeIconView out_icon;

    @FXML
    private Label transaction_date_label;

    @FXML
    private Label sender_label;

    @FXML
    private Label receiver_label;

    @FXML
    private Label amount_label;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
