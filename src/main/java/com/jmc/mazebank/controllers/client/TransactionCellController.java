package com.jmc.mazebank.controllers.client;

import com.jmc.mazebank.models.Model;
import com.jmc.mazebank.models.Transaction;
import com.jmc.mazebank.views.ViewFactory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

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
    private Button message_button;

    @FXML
    private Label amount_label;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sender_label.textProperty().bind(transaction.senderProperty());
        receiver_label.textProperty().bind(transaction.receiverProperty());
        amount_label.textProperty().bind(transaction.amountProperty().asString());
        transaction_date_label.textProperty().bind(transaction.dateProperty().asString());
        message_button.setOnAction(_ -> ViewFactory.getInstance().showMessageWindow(transaction.senderProperty().get(), transaction.messageProperty().get()));
        setTransactionIcons();
    }

    private void setTransactionIcons() {
        if (transaction.senderProperty().get().equals(Model.getInstance().getClient().payeeAddressProperty().get())) {
            in_icon.setFill(Color.rgb(240, 240, 240));
            out_icon.setFill(Color.RED);
        } else {
            in_icon.setFill(Color.GREEN);
            out_icon.setFill(Color.rgb(240, 240, 240));
        }
    }
}
