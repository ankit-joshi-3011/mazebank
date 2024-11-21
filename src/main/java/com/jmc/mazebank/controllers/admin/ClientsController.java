package com.jmc.mazebank.controllers.admin;

import com.jmc.mazebank.models.Client;
import com.jmc.mazebank.models.Model;
import com.jmc.mazebank.views.ClientCellFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    @FXML
    private ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().setClients();
        clients_listview.setItems(Model.getInstance().getClients());
        clients_listview.setCellFactory(_ -> new ClientCellFactory());
    }
}
