package com.jmc.mazebank.views;

import com.jmc.mazebank.controllers.admin.ClientCellController;
import com.jmc.mazebank.models.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ClientCellFactory extends ListCell<Client> {
    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/ClientCell.fxml"));

            ClientCellController controller = new ClientCellController(client);
            loader.setController(controller);

            setText(null);

            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
